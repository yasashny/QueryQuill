package com.yas.request_code_editor

import com.yas.model.LanguageType
import com.yas.model.TextType

internal fun textTypeToLanguageName(textType: TextType): LanguageType = when (textType) {
    TextType.JSON -> LanguageType.JSON
    TextType.XML -> LanguageType.XML
    TextType.PLAIN -> LanguageType.PLAIN
    TextType.OTHER -> LanguageType.OTHER
}