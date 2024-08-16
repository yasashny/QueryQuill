package com.yas.utils

import com.yas.model.ContentType
import com.yas.model.LanguageType


fun contentTypeToLanguageType(contentType: ContentType): LanguageType {
    return when (contentType) {
        ContentType.Text.HTML -> LanguageType.HTML
        ContentType.Image.JPEG -> LanguageType.OTHER
        ContentType.Application.JSON -> LanguageType.JSON
        ContentType.Text.PLAIN -> LanguageType.PLAIN
        ContentType.Image.PNG -> LanguageType.OTHER
        ContentType.Image.WEBP -> LanguageType.OTHER
        ContentType.Text.XML -> LanguageType.XML
    }
}