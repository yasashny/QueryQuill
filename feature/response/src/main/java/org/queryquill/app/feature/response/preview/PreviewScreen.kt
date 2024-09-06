package org.queryquill.app.feature.response.preview

import android.os.Build
import androidx.compose.runtime.Composable
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.feature.response.source.ResponseScreenSource
import java.io.File


@Composable
internal fun PreviewScreen(
    contentType: ContentType, file: File, codeEditorState: CodeEditorState
) {

    when (contentType) {
        ContentType.Text.HTML -> WebViewPage(file)
        ContentType.Image.JPEG -> Base64ImageDisplay(file, codeEditorState)

        ContentType.Application.JSON -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.JSON
            } else {
                LanguageType.OTHER
            }, file, codeEditorState
        )

        ContentType.Text.PLAIN -> ResponseScreenSource(
            LanguageType.PLAIN, file, codeEditorState
        )

        ContentType.Image.PNG -> Base64ImageDisplay(file, codeEditorState)

        ContentType.Image.WEBP -> Base64ImageDisplay(file, codeEditorState)

        ContentType.Text.XML -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.XML
            } else {
                LanguageType.OTHER
            }, file, codeEditorState
        )

        ContentType.Image.BMP -> Base64ImageDisplay(file, codeEditorState)
        ContentType.Image.HEIC -> Base64ImageDisplay(file, codeEditorState)
        ContentType.Image.HEIF -> Base64ImageDisplay(file, codeEditorState)
    }
}