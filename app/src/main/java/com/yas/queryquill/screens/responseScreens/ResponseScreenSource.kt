package com.yas.queryquill.screens.responseScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.queryquill.components.codeEditor.CodeEditor
import com.yas.queryquill.components.codeEditor.LanguageType

@Composable
fun ResponseScreenSource(text: String, languageType: LanguageType) {
    CodeEditor(
        initialText = text,
        modifier = Modifier.fillMaxSize(),
        isEditable = false,
        languageType = languageType,
        isBasicDisplayMode = true
    )
}