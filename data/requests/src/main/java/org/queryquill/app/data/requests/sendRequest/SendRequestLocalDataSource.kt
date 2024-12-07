package org.queryquill.app.data.requests.sendRequest

import android.content.Context
import android.net.Uri
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.prepareRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.cio.readChannel
import io.ktor.util.flattenEntries
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.InternalAPI
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.data.requests.models.AuthStateDTO
import org.queryquill.app.data.requests.models.BodyStateDTO
import org.queryquill.app.data.requests.models.HttpTypeDTO
import org.queryquill.app.data.requests.models.KeyValueDTO
import org.queryquill.app.data.requests.models.MultipartFormStateDTO
import org.queryquill.app.data.requests.models.RequestDTO
import org.queryquill.app.data.requests.models.ResponseDTO
import org.queryquill.app.data.requests.utils.encodeBase64
import org.queryquill.app.data.requests.utils.fileFromContentUri
import org.queryquill.app.data.requests.utils.fileNameByUri
import org.queryquill.app.data.requests.utils.getMIMEType
import org.queryquill.app.data.requests.utils.mimeTypeToContentType
import java.io.File

internal class SendRequestLocalDataSource(
    private val client: HttpClient, private val context: Context
) {

    suspend fun sendRequest(model: RequestDTO): ResponseDTO {
        val fileNamePrefix = "${model.id}_response"
        try {
            val url = getUrl(model)
            val request =
                prepareRequest(url = url, model = model)

            var fileName = "${fileNamePrefix}.txt"
            val file = File(context.filesDir, fileName)
            file.writeText("")

            val response = request.execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.body()
                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.exhausted()) {
                        val bytes = packet.readByteArray()
                        file.appendBytes(bytes)
                    }
                }
                return@execute httpResponse
            }

            val elapsedTime = calculateElapsedTime(response)
            val contentLength = file.length().toString()
            val status = response.status.value.toString()
            val contentType = getContentType(response)
            fileName = "${fileNamePrefix}.${contentTypeToExtension[contentType] ?: "txt"}"
            val newFile = File(file.parentFile, fileName)
            file.renameTo(newFile)
            val headers = extractHeaders(response.headers)

            return ResponseDTO(
                status = status,
                fileName = fileName,
                contentLength = contentLength,
                time = elapsedTime,
                contentType = contentType,
                headers = headers
            )

        } catch (e: Exception) {
            val message = when (e) {
                is UnresolvedAddressException -> "Couldn't resolve host name"
                is NullPointerException -> "File not found"
                else -> e.message.toString()
            }
            val fileName = "${fileNamePrefix}.txt"
            val file = File(context.filesDir, fileName)
            file.writeText(message)
            return ResponseDTO.errorType(fileName)
        }
    }

    private fun extractHeaders(headers: Headers): List<KeyValueDTO> {
        return headers.flattenEntries().map { KeyValueDTO(it.first, it.second) }
    }

    private fun calculateElapsedTime(response: HttpResponse): String {
        val elapsed = response.responseTime.timestamp - response.requestTime.timestamp
        return elapsed.toString()
    }

    private fun getContentType(response: HttpResponse): ContentType {
        val contentTypeStr = response.contentType()?.contentType
        val contentSubtypeStr = response.contentType()?.contentSubtype
        val mimeType = mimeTypeToContentType("${contentTypeStr}/${contentSubtypeStr}")
        return mimeType ?: ContentType.Text.PLAIN
    }

    private fun getUrl(model: RequestDTO): String {
        return if (model.url.startsWith("http://") || model.url.startsWith("https://")) {
            model.url
        } else {
            "http://" + model.url
        }
    }

    private val contentTypeToExtension = mapOf(
        ContentType.Text.HTML to "html",
        ContentType.Image.JPEG to "jpeg",
        ContentType.Application.JSON to "json",
        ContentType.Image.PNG to "png",
        ContentType.Image.WEBP to "webp",
        ContentType.Text.XML to "xml",
        ContentType.Image.BMP to "bmp",
        ContentType.Image.HEIC to "heic",
        ContentType.Image.HEIF to "heif"
    )

    @OptIn(InternalAPI::class)
    private suspend fun prepareRequest(
        url: String, model: RequestDTO
    ): HttpStatement = client.prepareRequest(url) {

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
                                                HttpHeaders.ContentDisposition, "filename=\"${
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
                val file = File(context.filesDir, bodyState.textFileName)
                if (!file.exists()) {
                    file.writeText("")
                }
                body = file.readChannel()
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
}
