package com.yas.response.preview

import android.os.Build
import androidx.compose.runtime.Composable
import com.yas.model.ContentType
import com.yas.model.LanguageType
import com.yas.response.ResponseScreenSource
import java.net.URI


@Composable
internal fun ResponseScreenPreview(
    fileName: String, contentType: ContentType, getTextFileUri: (textFileName: String) -> URI
) {

    when (contentType) {
        ContentType.Text.HTML -> WebViewPage(fileName, getTextFileUri)
        ContentType.Image.JPEG -> Base64ImageDisplay(fileName, getTextFileUri)

        ContentType.Application.JSON -> ResponseScreenSource(
            fileName, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.JSON
            } else {
                LanguageType.OTHER
            }, getTextFileUri
        )

        ContentType.Text.PLAIN -> ResponseScreenSource(
            fileName, LanguageType.PLAIN, getTextFileUri
        )

        ContentType.Image.PNG -> Base64ImageDisplay(fileName, getTextFileUri)

        ContentType.Image.WEBP -> Base64ImageDisplay(fileName, getTextFileUri)

        ContentType.Text.XML -> ResponseScreenSource(
            fileName, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.XML
            } else {
                LanguageType.OTHER
            }, getTextFileUri
        )

        ContentType.Image.BMP -> Base64ImageDisplay(fileName, getTextFileUri)
        ContentType.Image.HEIC -> Base64ImageDisplay(fileName, getTextFileUri)
        ContentType.Image.HEIF -> Base64ImageDisplay(fileName, getTextFileUri)
    }
}