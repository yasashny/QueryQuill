package com.yas.response.preview

import android.os.Build
import androidx.compose.runtime.Composable
import com.yas.model.ContentType
import com.yas.model.LanguageType
import com.yas.response.source.ResponseScreenSource
import java.io.File


@Composable
internal fun ResponseScreenPreview(
    contentType: ContentType, file: File
) {

    when (contentType) {
        ContentType.Text.HTML -> WebViewPage(file)
        ContentType.Image.JPEG -> Base64ImageDisplay(file)

        ContentType.Application.JSON -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.JSON
            } else {
                LanguageType.OTHER
            }, file
        )

        ContentType.Text.PLAIN -> ResponseScreenSource(
            LanguageType.PLAIN, file
        )

        ContentType.Image.PNG -> Base64ImageDisplay(file)

        ContentType.Image.WEBP -> Base64ImageDisplay(file)

        ContentType.Text.XML -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.XML
            } else {
                LanguageType.OTHER
            }, file
        )

        ContentType.Image.BMP -> Base64ImageDisplay(file)
        ContentType.Image.HEIC -> Base64ImageDisplay(file)
        ContentType.Image.HEIF -> Base64ImageDisplay(file)
    }
}