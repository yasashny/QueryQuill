package com.yas.response

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.model.LanguageType
import com.yas.ui.CodeEditor

@Composable
internal fun ResponseScreenSource(text: String, languageType: LanguageType) {
    Text(text = text)
//    CodeEditor(
//        initialText = text,
//        modifier = Modifier.fillMaxSize(),
//        isEditable = false,
//        languageType = languageType,
//        isBasicDisplayMode = true
//    )
}