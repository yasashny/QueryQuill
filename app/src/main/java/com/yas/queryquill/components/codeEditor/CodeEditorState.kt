package com.yas.queryquill.components.codeEditor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.widget.CodeEditor

data class CodeEditorState(
    var editor: CodeEditor? = null, val initialContent: Content = Content()
) {
    var content by mutableStateOf(initialContent)
}