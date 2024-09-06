package org.queryquill.app.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.github.rosemoe.sora.text.Content
import org.queryquill.app.core.model.CodeEditorState

@Composable
fun rememberCodeEditorState(
    initialContent: Content = Content()
) = remember {
    CodeEditorState(
        initialContent = initialContent
    )
}