package com.yas.utils

import android.os.Build
import com.yas.model.ContentType
import com.yas.model.LanguageType


fun contentTypeToLanguageType(contentType: ContentType): LanguageType {
    return when (contentType) {
        ContentType.Text.HTML -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.HTML
        } else {
            LanguageType.OTHER
        }

        ContentType.Image.JPEG -> LanguageType.OTHER
        ContentType.Application.JSON -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.JSON
        } else {
            LanguageType.OTHER
        }

        ContentType.Text.PLAIN -> LanguageType.PLAIN
        ContentType.Image.PNG -> LanguageType.OTHER
        ContentType.Image.WEBP -> LanguageType.OTHER
        ContentType.Text.XML -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.XML
        } else {
            LanguageType.OTHER
        }

        ContentType.Image.BMP -> LanguageType.OTHER
        ContentType.Image.HEIC -> LanguageType.OTHER
        ContentType.Image.HEIF -> LanguageType.OTHER
    }
}