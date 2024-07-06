package com.yas.data.sendRequest

import android.content.Context
import android.net.Uri
import com.yas.data.utils.encodeBase64
import com.yas.data.utils.fileFromContentUri
import com.yas.data.utils.fileNameByUri
import com.yas.data.utils.getMIMEType
import com.yas.domain.requestsDb.models.HttpType
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.states.AuthState
import com.yas.domain.requestsDb.states.BodyState
import com.yas.domain.requestsDb.states.MultipartFormState
import com.yas.domain.sendRequest.ResponseModel
import com.yas.domain.sendRequest.SendRequestModel
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
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import io.ktor.util.network.UnresolvedAddressException
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
            val body = response.readBytes()
            val contentLength = body.size.toString()
            val status = response.status.value.toString()
            val contentType = response.contentType()?.contentType
            val contentSubtype = response.contentType()?.contentSubtype

            return ResponseModel(
                status = status,
                body = body,
                contentLength = contentLength,
                time = elapsedTime,
                contentType = contentType,
                contentSubtype = contentSubtype
            )

        } catch (e: ConnectException) {
            return ResponseModel.errorType(e.message.toString().encodeToByteArray())
        } catch (e: UnknownHostException) {
            return ResponseModel.errorType(e.message.toString().encodeToByteArray())
        } catch (e: RedirectResponseException) {
            return ResponseModel.errorType(e.message.encodeToByteArray())
        } catch (e: ResponseException) {
            return ResponseModel.errorType(e.message.toString().encodeToByteArray())
        } catch (e: UnresolvedAddressException) {
            return ResponseModel.errorType("Couldn't resolve host name".encodeToByteArray())
        } catch (e: NullPointerException) {
            return ResponseModel.errorType("File not found".encodeToByteArray())
        } catch (e: Exception) {
            return ResponseModel.errorType(e.message.toString().encodeToByteArray())
        }
    }
}
