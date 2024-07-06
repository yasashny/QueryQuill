package com.yas.queryquill.screens.responseScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.queryquill.components.codeEditor.CodeEditor
import com.yas.queryquill.components.codeEditor.CodeEditorState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResponseScreenSource(text: String) {
    Scaffold {
        val editorState = CodeEditorState()
        editorState.initialContent.insert(0, 0, text)
        CodeEditor(state = editorState, modifier = Modifier.fillMaxSize(), isEditable = false)
    }
}