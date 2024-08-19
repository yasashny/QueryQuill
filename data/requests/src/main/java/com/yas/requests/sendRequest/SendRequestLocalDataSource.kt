package com.yas.requests.sendRequest

import android.content.Context
import android.net.Uri
import com.yas.model.ContentType
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
import com.yas.requests.utils.mimeTypeToContentType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.prepareRequest
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import io.ktor.util.cio.readChannel
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
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
            val request = client.prepareRequest(url) {

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
                            body =
                                fileFromContentUri(context = context, bodyState.uri).readChannel()
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
            var fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText("")
            val response = request.execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.body()
                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.isEmpty) {
                        val bytes = packet.readBytes()
                        file.appendBytes(bytes)
                    }
                }
                return@execute httpResponse
            }
            val requestTime = response.requestTime
            val responseTime = response.responseTime
            val elapsedTime = (responseTime.timestamp - requestTime.timestamp).toString()
            val contentLength = file.length().toString()
            val status = response.status.value.toString()
            val contentTypeStr = response.contentType()?.contentType
            val contentSubtypeStr = response.contentType()?.contentSubtype
            var contentType: ContentType = ContentType.Text.PLAIN

            when (mimeTypeToContentType("${contentTypeStr}/${contentSubtypeStr}")) {
                ContentType.Text.HTML -> {
                    contentType = ContentType.Text.HTML
                    fileName = "${model.id}_response.html"
                }

                ContentType.Image.JPEG -> {
                    contentType = ContentType.Image.JPEG
                    fileName = "${model.id}_response.jpeg"
                }

                ContentType.Application.JSON -> {
                    contentType = ContentType.Application.JSON
                    fileName = "${model.id}_response.json"
                }

                ContentType.Text.PLAIN -> {}
                ContentType.Image.PNG -> {
                    contentType = ContentType.Image.PNG
                    fileName = "${model.id}_response.png"
                }

                ContentType.Image.WEBP -> {
                    contentType = ContentType.Image.WEBP
                    fileName = "${model.id}_response.webp"
                }

                ContentType.Text.XML -> {
                    contentType = ContentType.Text.XML
                    fileName = "${model.id}_response.xml"
                }

                null -> {}
                ContentType.Image.BMP -> {
                    contentType = ContentType.Image.BMP
                    fileName = "${model.id}_response.bmp"
                }

                ContentType.Image.HEIC -> {
                    contentType = ContentType.Image.HEIC
                    fileName = "${model.id}_response.heic"
                }

                ContentType.Image.HEIF -> {
                    contentType = ContentType.Image.HEIF
                    fileName = "${model.id}_response.heif"
                }
            }
            val newFile = File(file.parentFile, fileName)
            file.renameTo(newFile)

            val headers = mutableListOf<KeyValueDTO>()
            response.headers.forEach { key, values ->
                values.forEach { value ->
                    headers.add(KeyValueDTO(key = key, value = value))
                }
            }

            return ResponseDTO(
                status = status,
                fileName = fileName,
                contentLength = contentLength,
                time = elapsedTime,
                contentType = contentType,
                headers = headers
            )

        } catch (e: ConnectException) {
            val fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText(e.message.toString())
            return ResponseDTO.errorType(fileName)
        } catch (e: UnknownHostException) {
            val fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText(e.message.toString())
            return ResponseDTO.errorType(fileName)
        } catch (e: RedirectResponseException) {
            val fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText(e.message)
            return ResponseDTO.errorType(fileName)
        } catch (e: ResponseException) {
            val fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText(e.message.toString())
            return ResponseDTO.errorType(fileName)
        } catch (e: UnresolvedAddressException) {
            val fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText("Couldn't resolve host name")
            return ResponseDTO.errorType(fileName)
        } catch (e: NullPointerException) {
            val fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText("File not found")
            return ResponseDTO.errorType(fileName)
        } catch (e: Exception) {
            val fileName = "${model.id}_response.txt"
            val file = File(context.filesDir, fileName)
            file.writeText(e.message.toString())
            return ResponseDTO.errorType(fileName)
        }
    }
}
