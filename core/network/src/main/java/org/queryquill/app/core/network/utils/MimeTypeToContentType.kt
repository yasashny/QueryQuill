package org.queryquill.app.core.network.utils

import org.queryquill.app.core.model.ContentType

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
        "image/heif" -> ContentType.Image.HEIF
        "image/heic" -> ContentType.Image.HEIC
        else -> null
    }
}