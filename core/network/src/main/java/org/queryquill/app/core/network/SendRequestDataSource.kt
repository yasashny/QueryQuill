package org.queryquill.app.core.network

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
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.core.network.utils.createErrorResponse
import org.queryquill.app.core.network.utils.encodeBase64
import org.queryquill.app.core.network.utils.fileFromContentUri
import org.queryquill.app.core.utils.fileNameByUri
import org.queryquill.app.core.utils.getMIMEType
import org.queryquill.app.core.network.utils.mimeTypeToContentType
import java.io.File

class SendRequestDataSource(
    private val client: HttpClient, private val context: Context
) {

    suspend fun sendRequest(model: RequestModel): ResponseModel {
        val fileNamePrefix = "${model.id}_response"
        try {
            val url = getUrl(model)
            val request = prepareRequest(url = url, model = model)

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

            return ResponseModel(
                id = model.id,
                status = status,
                fileName = fileName,
                contentLength = contentLength,
                time = elapsedTime,
                contentType = contentType,
                headers = ImmutableList(headers)
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
            return createErrorResponse(fileName)
        }
    }

    private fun extractHeaders(headers: Headers): List<KeyValue> {
        return headers.flattenEntries().map {
            KeyValue(
                it.first, it.second
            )
        }
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

    private fun getUrl(model: RequestModel): String {
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
        url: String, model: RequestModel
    ): HttpStatement = client.prepareRequest(url) {

        url {
            model.query.list.forEach { keyValue ->
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
            model.header.list.forEach { keyValue ->
                if (keyValue != KeyValue.empty()) {
                    append(keyValue.key, keyValue.value)
                }
            }
        }

        when (val bodyState = model.bodyState) {
            is BodyState.BinaryFile -> {
                if (bodyState.uri.uri != Uri.EMPTY) {
                    fileNameByUri(context.contentResolver, bodyState.uri.uri)
                    body = fileFromContentUri(context = context, bodyState.uri.uri).readChannel()
                }
            }

            is BodyState.FormUrlEncoded -> {
                body = FormDataContent(Parameters.build {
                    bodyState.list.list.forEach { keyValue ->
                        if (keyValue != KeyValue.empty()) {
                            append(keyValue.key, keyValue.value)
                        }
                    }
                })
            }

            is BodyState.MultipartForm -> {
                body = MultiPartFormDataContent(
                    formData {
                        bodyState.multipart.list.forEach { multipartState ->
                            when (multipartState) {
                                is MultipartFormState.BinaryFile -> {
                                    if (multipartState.uri.uri != Uri.EMPTY) {
                                        append(multipartState.title, fileFromContentUri(
                                            context = context, multipartState.uri.uri
                                        ).readBytes(), Headers.build {
                                            append(
                                                HttpHeaders.ContentType,
                                                getMIMEType(context, multipartState.uri.uri)
                                            )
                                            append(
                                                HttpHeaders.ContentDisposition, "filename=\"${
                                                    fileNameByUri(
                                                        context.contentResolver,
                                                        multipartState.uri.uri
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
                val file = File(context.filesDir, bodyState.textFileName)
                if (!file.exists()) {
                    file.writeText("")
                }
                body = file.readChannel()
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
}
