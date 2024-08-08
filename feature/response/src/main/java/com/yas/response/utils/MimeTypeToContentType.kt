package com.yas.response.utils

import com.yas.response.ContentType

internal fun mimeTypeToContentType(mimeType: String?): ContentType? {
    return when (mimeType) {
        "text/html" -> ContentType.Text.HTML
        "text/plain" -> ContentType.Text.PLAIN
        "text/xml" -> ContentType.Text.XML
        "application/json" -> ContentType.Application.JSON
        "image/jpeg" -> ContentType.Image.JPEG
        "image/png" -> ContentType.Image.PNG
        "image/webp" -> ContentType.Image.WEBP
        else -> null
    }
}