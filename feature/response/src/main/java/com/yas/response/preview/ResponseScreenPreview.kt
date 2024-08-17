package com.yas.response.preview

import androidx.compose.runtime.Composable
import com.yas.model.ContentType
import com.yas.model.LanguageType
import com.yas.response.ResponseScreenSource
import java.io.File
import java.net.URI


@Composable
internal fun ResponseScreenPreview(
    fileName: String, contentType: ContentType,
    getTextFileUri: (textFileName: String) -> URI
) {
    val file = File(getTextFileUri(fileName))

    when (contentType) {
        ContentType.Text.HTML -> {}//WebViewPage(html = body.byteArray.decodeToString())
        ContentType.Image.JPEG -> Base64ImageDisplay(file)

        ContentType.Application.JSON -> ResponseScreenSource(
            fileName, LanguageType.JSON, getTextFileUri
        )

        ContentType.Text.PLAIN -> ResponseScreenSource(
            fileName, LanguageType.PLAIN, getTextFileUri
        )

        ContentType.Image.PNG -> Base64ImageDisplay(file)

        ContentType.Image.WEBP -> Base64ImageDisplay(file)

        ContentType.Text.XML -> ResponseScreenSource(
            fileName, LanguageType.XML, getTextFileUri
        )

        ContentType.Image.BMP -> Base64ImageDisplay(file)
    }
}