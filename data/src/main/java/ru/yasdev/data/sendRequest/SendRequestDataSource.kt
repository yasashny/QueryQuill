package ru.yasdev.data.sendRequest

import android.content.Context
import android.net.Uri
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.fromFilePath
import io.ktor.util.InternalAPI
import ru.yasdev.data.utils.encodeBase64
import ru.yasdev.data.utils.fileFromContentUri
import ru.yasdev.data.utils.fileNameByUri
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.states.AuthState
import ru.yasdev.domain.requestsDb.states.BodyState
import ru.yasdev.domain.requestsDb.states.MultipartFormState
import ru.yasdev.domain.sendRequest.SendRequestModel

class SendRequestDataSource(private val client: HttpClient, private val context: Context) {

    @OptIn(InternalAPI::class)
    suspend fun sendRequest(model: SendRequestModel) {
        val response = client.request(model.url) {

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

            body = when (val bodyState = model.bodyState) {
                is BodyState.BinaryFile -> {
                    fileFromContentUri(context = context, bodyState.uri).readBytes()
                }

                is BodyState.FormUrlEncoded -> {
                    FormDataContent(Parameters.build {
                        bodyState.list.forEach { keyValue ->
                            if (keyValue != KeyValue.empty()) {
                                append(keyValue.key, keyValue.value)
                            }
                        }
                    })
                }

                is BodyState.MultipartForm -> {
                    MultiPartFormDataContent(
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
                                                    ContentType.fromFilePath(fileNameByUri(context.contentResolver, multipartState.uri))
                                                        .firstOrNull()?.toString() ?: ContentType.Application.OctetStream.toString()
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

                BodyState.NoBody -> {}
                is BodyState.Text -> {
                    bodyState.text
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
}
