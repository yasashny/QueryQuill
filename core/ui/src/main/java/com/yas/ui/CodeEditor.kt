package com.yas.ui

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yas.model.CodeEditorState
import com.yas.model.LanguageType
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.widget.CodeEditor

@Composable
fun CodeEditor(
    modifier: Modifier = Modifier,
    state: CodeEditorState,
    isEditable: Boolean = true,
    isBasicDisplayMode: Boolean,
    languageType: LanguageType
) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            setCodeEditorFactory(
                languageType = languageType,
                context = context,
                state = state,
                isEditable = isEditable,
                isBasicDisplayMode = isBasicDisplayMode
            )
        },
        modifier = modifier,
        onRelease = {
            it.release()
        },
        update = {
            it.setText(state.content)
        }
    )
}


private fun setCodeEditorFactory(
    languageType: LanguageType, context: Context, state: CodeEditorState, isEditable: Boolean,
    isBasicDisplayMode: Boolean,
): CodeEditor {
    val editor = CodeEditor(context)
    editor.apply {
        this.isEditable = isEditable
        colorScheme = TextMateColorScheme.create(ThemeRegistry.getInstance())
        this.isBasicDisplayMode = isBasicDisplayMode
        val languageScopeName = languageType.code
        if (languageScopeName != null) {
            val language = TextMateLanguage.create(
                languageScopeName, true
            )
            setEditorLanguage(language)
        }

        isScalable = false
        typefaceText = Typeface.MONOSPACE
        nonPrintablePaintingFlags =
            CodeEditor.FLAG_DRAW_WHITESPACE_LEADING or CodeEditor.FLAG_DRAW_LINE_SEPARATOR or CodeEditor.FLAG_DRAW_WHITESPACE_IN_SELECTION
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        isWordwrap = true
    }
    state.editor = editor
    return editor
}


//@Composable
//fun CodeEditor(
//    modifier: Modifier = Modifier,
//    content: Content,
//    isEditable: Boolean = true,
//    isBasicDisplayMode: Boolean,
//    languageType: LanguageType
//) {
//
//    AndroidView(factory = { cxt ->
//        CodeEditor(cxt)
//    }, modifier = modifier, update = {
//        it.isEditable = isEditable
//        it.colorScheme = TextMateColorScheme.create(ThemeRegistry.getInstance())
//        it.isBasicDisplayMode = isBasicDisplayMode
//        val languageScopeName = languageType.code
//        if (languageScopeName != null) {
//            val language = TextMateLanguage.create(
//                languageScopeName, true
//            )
//            it.setEditorLanguage(language)
//        }
//
//        it.isScalable = false
//        it.typefaceText = Typeface.MONOSPACE
//        it.nonPrintablePaintingFlags =
//            CodeEditor.FLAG_DRAW_WHITESPACE_LEADING or CodeEditor.FLAG_DRAW_LINE_SEPARATOR or CodeEditor.FLAG_DRAW_WHITESPACE_IN_SELECTION
//        it.layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        it.setText(content)
//        it.isWordwrap = true
//
//
//    }, onRelease = {
//        it.release()
//    })
//}