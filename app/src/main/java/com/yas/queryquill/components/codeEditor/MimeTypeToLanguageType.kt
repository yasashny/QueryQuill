package com.yas.queryquill.components.codeEditor

fun mimeTypeToLanguageType(mimeType: String?): LanguageType {
    return when (mimeType) {
        "text/html" -> LanguageType.HTML
        "application/json" -> LanguageType.JSON
        "text/xml" -> LanguageType.XML
        "text/plain" -> LanguageType.PLAIN
        else -> LanguageType.OTHER
    }
}