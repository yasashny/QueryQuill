package com.yas.queryquill.components.codeEditor

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import io.github.rosemoe.sora.widget.CodeEditor

@Composable
fun CodeEditor(
    modifier: Modifier = Modifier, state: CodeEditorState, updateRequest: (String) -> Unit
) {
    val context = LocalContext.current
    val editor = remember {
        setCodeEditorFactory(
            context = context, state = state
        )
    }
    AndroidView(factory = {
        editor.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            text.addContentListener(CodeEditorListener(updateRequest))
        }
    }, modifier = modifier, update = {
        it.isWordwrap = true
        it.isScalable = false
        it.typefaceText = Typeface.MONOSPACE
        it.nonPrintablePaintingFlags =
            CodeEditor.FLAG_DRAW_WHITESPACE_LEADING or CodeEditor.FLAG_DRAW_LINE_SEPARATOR or CodeEditor.FLAG_DRAW_WHITESPACE_IN_SELECTION

    }, onRelease = {
        it.release()
    })
}


private fun setCodeEditorFactory(
    context: Context, state: CodeEditorState
): CodeEditor {
    val editor = CodeEditor(context)
    editor.apply {
        setText(state.content)
    }
    state.editor = editor
    return editor
}


private class CodeEditorListener(val updateRequest: (String) -> Unit) : ContentListener {

    override fun beforeReplace(content: Content) {
        updateRequest(content.toString())
    }

    override fun afterInsert(
        content: Content,
        startLine: Int,
        startColumn: Int,
        endLine: Int,
        endColumn: Int,
        insertedContent: CharSequence
    ) {
        updateRequest(content.toString())
    }

    override fun afterDelete(
        content: Content,
        startLine: Int,
        startColumn: Int,
        endLine: Int,
        endColumn: Int,
        deletedContent: CharSequence
    ) {
        updateRequest(content.toString())
    }
}