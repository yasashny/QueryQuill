package com.yas.requests.utils

import com.yas.model.ContentType

internal fun mimeTypeToContentType(mimeType: String?): ContentType? {
    return when (mimeType) {
        "text/html" -> ContentType.Text.HTML
        "text/plain" -> ContentType.Text.PLAIN
        "text/xml" -> ContentType.Text.XML
        "application/json" -> ContentType.Application.JSON
        "image/jpeg" -> ContentType.Image.JPEG
        "image/png" -> ContentType.Image.PNG
        "image/webp" -> ContentType.Image.WEBP
        "image/bmp" -> ContentType.Image.BMP
        else -> null
    }
}