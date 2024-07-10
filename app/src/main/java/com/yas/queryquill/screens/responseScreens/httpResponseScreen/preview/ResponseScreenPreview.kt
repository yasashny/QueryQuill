package com.yas.queryquill.screens.responseScreens.httpResponseScreen.preview

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.yas.queryquill.components.codeEditor.ContentType
import com.yas.queryquill.components.codeEditor.LanguageType
import com.yas.queryquill.components.codeEditor.mimeTypeToContentType
import com.yas.queryquill.screens.responseScreens.ResponseScreenSource


@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResponseScreenPreview(body: ByteArray, mimeType: String?, contentSubtype: String?) {

    val contentType = mimeTypeToContentType("$mimeType/$contentSubtype")
    when (contentType) {
        ContentType.Text.HTML -> WebViewPage(html = body.decodeToString())
        ContentType.Image.JPEG -> Base64ImageDisplay(
            base64String = java.util.Base64.getEncoder().encodeToString(body),
            utf8String = body.decodeToString()
        )

        ContentType.Application.JSON -> ResponseScreenSource(
            body.decodeToString(), LanguageType.JSON
        )

        ContentType.Text.PLAIN -> ResponseScreenSource(body.decodeToString(), LanguageType.PLAIN)
        ContentType.Image.PNG -> Base64ImageDisplay(
            base64String = java.util.Base64.getEncoder().encodeToString(body),
            utf8String = body.decodeToString()
        )

        ContentType.Image.WEBP -> Base64ImageDisplay(
            base64String = java.util.Base64.getEncoder().encodeToString(body),
            utf8String = body.decodeToString()
        )

        ContentType.Text.XML -> ResponseScreenSource(body.decodeToString(), LanguageType.XML)
        null -> ResponseScreenSource(body.decodeToString(), LanguageType.PLAIN)
    }
}