package com.yas.queryquill.components.codeEditor

import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import io.github.rosemoe.sora.widget.CodeEditor

@Composable
fun CodeEditor(
    modifier: Modifier = Modifier,
    initialText: String,
    isEditable: Boolean = true,
    isBasicDisplayMode: Boolean,
    languageType: LanguageType,
    updateRequest: (String) -> Unit = {}
) {

    AndroidView(factory = { cxt ->
        CodeEditor(cxt)
    }, modifier = modifier, update = {
        it.isEditable = isEditable
        it.colorScheme = TextMateColorScheme.create(ThemeRegistry.getInstance())
        it.isBasicDisplayMode = isBasicDisplayMode
        val languageScopeName = languageType.code
        if (languageScopeName != null) {
            val language = TextMateLanguage.create(
                languageScopeName, true
            )
            it.setEditorLanguage(language)
        }

        it.isScalable = false
        it.typefaceText = Typeface.MONOSPACE
        it.nonPrintablePaintingFlags =
            CodeEditor.FLAG_DRAW_WHITESPACE_LEADING or CodeEditor.FLAG_DRAW_LINE_SEPARATOR or CodeEditor.FLAG_DRAW_WHITESPACE_IN_SELECTION
        it.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val content = Content()
        content.insert(0, 0, initialText)
        it.setText(content)
        it.text.addContentListener(CodeEditorListener(updateRequest))
        it.isWordwrap = true


    }, onRelease = {
        it.release()
    })
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