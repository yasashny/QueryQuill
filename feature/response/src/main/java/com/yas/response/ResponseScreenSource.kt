package com.yas.response

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val state = rememberCodeEditorState()
    Text(text = fileName)
    CodeEditor(
        state = state,
        modifier = Modifier.fillMaxSize(),
        isEditable = false,
        languageType = LanguageType.PLAIN,
        isBasicDisplayMode = true,
        file = file
    )
}