package ru.yasdev.data.sendRequest

import android.content.Context
import android.net.Uri
import io.ktor.client.HttpClient
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.readBytes
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import io.ktor.util.network.UnresolvedAddressException
import ru.yasdev.data.utils.encodeBase64
import ru.yasdev.data.utils.fileFromContentUri
import ru.yasdev.data.utils.fileNameByUri
import ru.yasdev.data.utils.getMIMEType
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.states.AuthState
import ru.yasdev.domain.requestsDb.states.BodyState
import ru.yasdev.domain.requestsDb.states.MultipartFormState
import ru.yasdev.domain.sendRequest.ResponseModel
import ru.yasdev.domain.sendRequest.SendRequestModel
import java.net.ConnectException
import java.net.UnknownHostException

class SendRequestDataSource(private val client: HttpClient, private val context: Context) {

    @OptIn(InternalAPI::class)
    suspend fun sendRequest(model: SendRequestModel): ResponseModel {
        try {
            val url = if (model.url.startsWith("http://") || model.url.startsWith("https://")) {
                model.url
            } else {
                "http://" + model.url
            }
            val response = client.request(url) {

                url {
                    model.query.forEach { keyValue ->
                        if (keyValue != KeyValue.empty()) {
                            parameters.append(keyValue.key, keyValue.value)
                        }
                    }
                }

                method = when (model.type) {
                    HttpType.GET -> HttpMethod.Get
                    HttpType.POST -> HttpMethod.Post
                    HttpType.PUT -> HttpMethod.Put
                    HttpType.PATCH -> HttpMethod.Patch
                    HttpType.DELETE -> HttpMethod.Delete
                    HttpType.OPTIONS -> HttpMethod.Options
                    HttpType.HEAD -> HttpMethod.Head
                }

                headers {
                    model.headers.forEach { keyValue ->
                        if (keyValue != KeyValue.empty()) {
                            append(keyValue.key, keyValue.value)
                        }
                    }
                }

                when (val bodyState = model.bodyState) {
                    is BodyState.BinaryFile -> {
                        if (bodyState.uri != Uri.EMPTY) {
                            fileNameByUri(context.contentResolver, bodyState.uri)
                            body = fileFromContentUri(context = context, bodyState.uri).readBytes()
                        }
                    }

                    is BodyState.FormUrlEncoded -> {
                        body = FormDataContent(Parameters.build {
                            bodyState.list.forEach { keyValue ->
                                if (keyValue != KeyValue.empty()) {
                                    append(keyValue.key, keyValue.value)
                                }
                            }
                        })
                    }

                    is BodyState.MultipartForm -> {
                        body = MultiPartFormDataContent(
                            formData {
                                bodyState.multipart.forEach { multipartState ->
                                    when (multipartState) {
                                        is MultipartFormState.BinaryFile -> {
                                            if (multipartState.uri != Uri.EMPTY) {
                                                append(multipartState.title, fileFromContentUri(
                                                    context = context, multipartState.uri
                                                ).readBytes(), Headers.build {
                                                    append(
                                                        HttpHeaders.ContentType,
                                                        getMIMEType(context, multipartState.uri)
                                                    )
                                                    append(
                                                        HttpHeaders.ContentDisposition,
                                                        "filename=\"${
                                                            fileNameByUri(
                                                                context.contentResolver,
                                                                multipartState.uri
                                                            )
                                                        }\""
                                                    )
                                                })
                                            }
                                        }

                                        is MultipartFormState.Text -> {
                                            if (multipartState.keyValue != KeyValue.empty()) {
                                                append(
                                                    multipartState.keyValue.key,
                                                    multipartState.keyValue.value
                                                )
                                            }
                                        }
                                    }
                                }
                            }, boundary = "QUERY-QUILL-BOUNDARY"
                        )
                    }

                    BodyState.NoBody -> {

                    }

                    is BodyState.Text -> {
                        body = bodyState.text
                    }
                }

                when (val authState = model.auth) {
                    is AuthState.Basic -> {
                        headers.append(
                            HttpHeaders.Authorization,
                            "Basic " + encodeBase64("${authState.userName}:${authState.password}")
                        )
                    }

                    AuthState.NoAuth -> {}
                }
            }
            val requestTime = response.requestTime
            val responseTime = response.responseTime
            val elapsedTime = (responseTime.timestamp - requestTime.timestamp).toString()
            val body = response.readBytes().toString(Charsets.UTF_8)
            val contentLength = body.length.toString()
            val status = response.status.value.toString()
            return ResponseModel(
                status = status, body = body, contentLength = contentLength, time = elapsedTime
            )

        } catch (e: ConnectException) {
            return ResponseModel("Error", e.message.toString(), "--", "--")
        } catch (e: UnknownHostException) {
            return ResponseModel("Error", e.message.toString(), "--", "--")
        } catch (e: RedirectResponseException) {
            return ResponseModel("Error", e.message, "--", "--")
        } catch (e: ResponseException) {
            return ResponseModel("Error", e.message.toString(), "--", "--")
        } catch (e: UnresolvedAddressException) {
            return ResponseModel("Error", "Couldn't resolve host name", "--", "--")
        } catch (e: NullPointerException) {
            return ResponseModel("Error", "File not found", "--", "--")
        } catch (e: Exception) {
            return ResponseModel("Error", e.message.toString(), "--", "--")
        }
    }
}
