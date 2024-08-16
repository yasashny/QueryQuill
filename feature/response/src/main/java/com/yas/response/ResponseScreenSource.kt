package com.yas.response

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.yas.model.CodeEditorState
import com.yas.model.LanguageType
import com.yas.ui.CodeEditor
import com.yas.ui.rememberCodeEditorState
import java.io.File
import java.net.URI

@Composable
internal fun ResponseScreenSource(
    fileName: String,
    languageType: LanguageType,
    getTextFileUri: (textFileName: String) -> URI
) {
    val file = File(getTextFileUri(fileName))
    val state = CodeEditorState()
    CodeEditor(
        state = state,
        modifier = Modifier.fillMaxSize(),
        isEditable = false,
        languageType = languageType,
        isBasicDisplayMode = true,
        file = file
    )
}