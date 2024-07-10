package com.yas.queryquill.utils

import com.yas.domain.requestsDb.states.TextType
import com.yas.queryquill.components.codeEditor.LanguageType

fun textTypeToLanguageName(textType: TextType): LanguageType = when (textType) {
    TextType.JSON -> LanguageType.JSON
    TextType.XML -> LanguageType.XML
    TextType.PLAIN -> LanguageType.PLAIN
    TextType.OTHER -> LanguageType.OTHER
}