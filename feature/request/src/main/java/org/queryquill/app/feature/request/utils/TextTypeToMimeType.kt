package org.queryquill.app.feature.request.utils

import org.queryquill.app.core.model.TextType

internal fun TextType.toMimeType(): String {
    return when (this) {
        TextType.JSON -> "application/json"
        TextType.XML -> "text/xml"
        TextType.PLAIN -> "text/plain"
        TextType.OTHER -> ""
    }
}