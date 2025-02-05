package org.queryquill.app.core.network.prepareRequest

import android.content.Context
import android.net.Uri
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.FormBuilder
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.util.cio.readChannel
import io.ktor.utils.io.InternalAPI
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.network.utils.fileFromContentUri
import org.queryquill.app.core.utils.fileNameByUri
import org.queryquill.app.core.utils.getMIMEType
import java.io.File

internal fun HttpRequestBuilder.applyBody(bodyState: BodyState, context: Context) {
    when (bodyState) {
        is BodyState.BinaryFile -> {
            handleBinaryFile(bodyState.uri.uri, context)
        }

        is BodyState.FormUrlEncoded -> {
            handleFormUrlEncoded(bodyState.list.list)
        }

        is BodyState.MultipartForm -> {
            handleMultipartForm(bodyState.multipart.list, context)
        }

        BodyState.NoBody -> {}

        is BodyState.Text -> {
            handleTextBody(bodyState.textFileName, context)
        }
    }
}

@OptIn(InternalAPI::class)
private fun HttpRequestBuilder.handleBinaryFile(uri: Uri, context: Context) {
    if (uri != Uri.EMPTY) {
        fileNameByUri(context.contentResolver, uri)
        body = fileFromContentUri(context = context, uri).readChannel()
    }
}

@OptIn(InternalAPI::class)
private fun HttpRequestBuilder.handleFormUrlEncoded(list: List<KeyValue>) {
    body = FormDataContent(Parameters.build {
        list.forEach { keyValue ->
            if (keyValue != KeyValue.empty()) {
                append(keyValue.key, keyValue.value)
            }
        }
    })
}

@OptIn(InternalAPI::class)
private fun HttpRequestBuilder.handleMultipartForm(
    list: List<MultipartFormState>, context: Context
) {
    body = MultiPartFormDataContent(
        formData {
            list.forEach { multipartState ->
                when (multipartState) {
                    is MultipartFormState.BinaryFile -> {
                        handleMultipartBinaryFile(multipartState, context)
                    }

                    is MultipartFormState.Text -> {
                        handleMultipartText(multipartState)
                    }
                }
            }
        }, boundary = BOUNDARY
    )
}

private const val BOUNDARY = "QUERY-QUILL-BOUNDARY"

private fun FormBuilder.handleMultipartBinaryFile(
    multipartState: MultipartFormState.BinaryFile, context: Context
) {
    if (multipartState.uri.uri != Uri.EMPTY) {
        append(multipartState.title,
            fileFromContentUri(context = context, multipartState.uri.uri).readBytes(),
            Headers.build {
                append(HttpHeaders.ContentType, getMIMEType(context, multipartState.uri.uri))
                append(
                    HttpHeaders.ContentDisposition, "filename=\"${
                        fileNameByUri(
                            context.contentResolver, multipartState.uri.uri
                        )
                    }\""
                )
            })
    }
}

private fun FormBuilder.handleMultipartText(multipartState: MultipartFormState.Text) {
    if (multipartState.keyValue != KeyValue.empty()) {
        append(
            multipartState.keyValue.key, multipartState.keyValue.value
        )
    }
}

@OptIn(InternalAPI::class)
private fun HttpRequestBuilder.handleTextBody(fileName: String, context: Context) {
    val file = File(context.filesDir, fileName)
    if (!file.exists()) {
        file.writeText("")
    }
    body = file.readChannel()
}