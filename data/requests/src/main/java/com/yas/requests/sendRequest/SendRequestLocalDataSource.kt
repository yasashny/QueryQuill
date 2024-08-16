package com.yas.requests.sendRequest

import android.content.Context
import android.net.Uri
import com.yas.requests.models.AuthStateDTO
import com.yas.requests.models.BodyStateDTO
import com.yas.requests.models.HttpTypeDTO
import com.yas.requests.models.KeyValueDTO
import com.yas.requests.models.MultipartFormStateDTO
import com.yas.requests.models.RequestDTO
import com.yas.requests.models.ResponseDTO
import com.yas.requests.utils.encodeBase64
import com.yas.requests.utils.fileFromContentUri
import com.yas.requests.utils.fileNameByUri
import com.yas.requests.utils.getMIMEType
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
import io.ktor.util.cio.readChannel
import io.ktor.util.network.UnresolvedAddressException
import java.io.File
import java.net.ConnectException
import java.net.URI
import java.net.UnknownHostException

internal class SendRequestLocalDataSource(
    private val client: HttpClient, private val context: Context
) {

    @OptIn(InternalAPI::class)
    suspend fun sendRequest(model: RequestDTO, requestModelUri: URI?): ResponseDTO {
        try {
            val url = if (model.url.startsWith("http://") || model.url.startsWith("https://")) {
                model.url
            } else {
                "http://" + model.url
            }
            val response = client.request(url) {

                url {
                    model.query.forEach { keyValue ->
                        if (keyValue != KeyValueDTO.empty()) {
                            parameters.append(keyValue.key, keyValue.value)
                        }
                    }
                }

                method = when (model.type) {
                    HttpTypeDTO.GET -> HttpMethod.Get
                    HttpTypeDTO.POST -> HttpMethod.Post
                    HttpTypeDTO.PUT -> HttpMethod.Put
                    HttpTypeDTO.PATCH -> HttpMethod.Patch
                    HttpTypeDTO.DELETE -> HttpMethod.Delete
                    HttpTypeDTO.OPTIONS -> HttpMethod.Options
                    HttpTypeDTO.HEAD -> HttpMethod.Head
                }

                headers {
                    model.header.forEach { keyValue ->
                        if (keyValue != KeyValueDTO.empty()) {
                            append(keyValue.key, keyValue.value)
                        }
                    }
                }

                when (val bodyState = model.bodyState) {
                    is BodyStateDTO.BinaryFile -> {
                        if (bodyState.uri != Uri.EMPTY) {
                            fileNameByUri(context.contentResolver, bodyState.uri)
                            body = fileFromContentUri(context = context, bodyState.uri).readChannel()
                        }
                    }

                    is BodyStateDTO.FormUrlEncoded -> {
                        body = FormDataContent(Parameters.build {
                            bodyState.list.forEach { keyValue ->
                                if (keyValue != KeyValueDTO.empty()) {
                                    append(keyValue.key, keyValue.value)
                                }
                            }
                        })
                    }

                    is BodyStateDTO.MultipartForm -> {
                        body = MultiPartFormDataContent(
                            formData {
                                bodyState.multipart.forEach { multipartState ->
                                    when (multipartState) {
                                        is MultipartFormStateDTO.BinaryFile -> {
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

                                        is MultipartFormStateDTO.Text -> {
                                            if (multipartState.keyValue != KeyValueDTO.empty()) {
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

                    BodyStateDTO.NoBody -> {

                    }

                    is BodyStateDTO.Text -> {
                        body = File(requestModelUri!!).readChannel()
                    }
                }

                when (val authState = model.authState) {
                    is AuthStateDTO.Basic -> {
                        headers.append(
                            HttpHeaders.Authorization,
                            "Basic " + encodeBase64("${authState.userName}:${authState.password}")
                        )
                    }

                    AuthStateDTO.NoAuth -> {}
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
            val headers = mutableListOf<KeyValueDTO>()
            response.headers.forEach { key, values ->
                values.forEach { value ->
                    headers.add(KeyValueDTO(key = key, value = value))
                }
            }

            return ResponseDTO(
                status = status,
                body = body,
                contentLength = contentLength,
                time = elapsedTime,
                contentType = contentType,
                contentSubtype = contentSubtype,
                headers = headers
            )

        } catch (e: ConnectException) {
            return ResponseDTO.errorType(e.message.toString().encodeToByteArray())
        } catch (e: UnknownHostException) {
            return ResponseDTO.errorType(e.message.toString().encodeToByteArray())
        } catch (e: RedirectResponseException) {
            return ResponseDTO.errorType(e.message.encodeToByteArray())
        } catch (e: ResponseException) {
            return ResponseDTO.errorType(e.message.toString().encodeToByteArray())
        } catch (e: UnresolvedAddressException) {
            return ResponseDTO.errorType("Couldn't resolve host name".encodeToByteArray())
        } catch (e: NullPointerException) {
            return ResponseDTO.errorType("File not found".encodeToByteArray())
        } catch (e: Exception) {
            return ResponseDTO.errorType(e.message.toString().encodeToByteArray())
        }
    }
}
