package com.yas.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.yas.model.CodeEditorState
import io.github.rosemoe.sora.text.Content

@Composable
fun rememberCodeEditorState(
    initialContent: Content = Content()
) = remember {
    CodeEditorState(
        initialContent = initialContent
    )
}