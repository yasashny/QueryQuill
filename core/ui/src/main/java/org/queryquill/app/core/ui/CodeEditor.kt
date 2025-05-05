/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.core.ui

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.lang.EmptyLanguage
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.ContentListener
import io.github.rosemoe.sora.widget.CodeEditor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.LanguageType
import java.io.File
import java.io.InputStream

@Composable
fun CodeEditor(
    modifier: Modifier = Modifier,
    state: CodeEditorState,
    isEditable: Boolean = true,
    isBasicDisplayMode: Boolean,
    languageType: LanguageType,
    file: File,
    isWordWrap: Boolean = true,
) {

    val context = LocalContext.current
    val inputStream = file.inputStream()
    var isLoading by remember {
        mutableStateOf(true)
    }
    if (state.content.isEmpty()) {
        LaunchedEffect(file) {
            isLoading = true
            withContext(Dispatchers.IO) {
                inputStream.readInChunks(10000).forEach {
                    withContext(Dispatchers.Main) {
                        val line = state.content.lineCount - 1
                        val column = state.content.getColumnCount(line)
                        state.content.insert(line, column, it)
                    }
                }
            }
            inputStream.close()
            isLoading = false
        }
    } else {
        isLoading = false
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        AndroidView(factory = {
            setCodeEditorFactory(
                languageType = languageType,
                context = context,
                state = state,
                isEditable = isEditable,
                isBasicDisplayMode = isBasicDisplayMode,
                isWordWrap = isWordWrap
            )
        }, modifier = modifier, onRelease = {
            it.release()
        }, update = {
            it.setText(state.content)
        })
    }
}


private fun setCodeEditorFactory(
    languageType: LanguageType,
    context: Context,
    state: CodeEditorState,
    isEditable: Boolean,
    isBasicDisplayMode: Boolean,
    isWordWrap: Boolean
): CodeEditor {
    val editor = CodeEditor(context)
    editor.apply {
        this.isEditable = isEditable
        colorScheme = TextMateColorScheme.create(ThemeRegistry.getInstance())
        this.isBasicDisplayMode = isBasicDisplayMode

        isScalable = false
        typefaceText = Typeface.MONOSPACE
        nonPrintablePaintingFlags =
            CodeEditor.FLAG_DRAW_WHITESPACE_LEADING or CodeEditor.FLAG_DRAW_LINE_SEPARATOR or CodeEditor.FLAG_DRAW_WHITESPACE_IN_SELECTION
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        this.isWordwrap = isWordWrap
    }
    state.editor = editor
    enableDisableHighlighting(
        state.content, state.editor!!, context, languageType, isBasicDisplayMode
    )
    state.content.addContentListener(
        QQContentListener(
            state.editor!!, languageType, context, isBasicDisplayMode
        )
    )
    return editor
}

internal fun InputStream.readInChunks(chunkSize: Int = 100): Sequence<String> = sequence {
    val buffer = ByteArray(chunkSize)
    var bytesRead: Int

    while (this@readInChunks.read(buffer).also { bytesRead = it } != -1) {
        yield(String(buffer, 0, bytesRead))
    }
}

private class QQContentListener(
    private val editor: CodeEditor,
    private val languageType: LanguageType,
    private val context: Context,
    private val isBasicDisplayMode: Boolean
) : ContentListener {
    override fun beforeReplace(content: Content) {

    }

    override fun afterInsert(
        content: Content,
        startLine: Int,
        startColumn: Int,
        endLine: Int,
        endColumn: Int,
        insertedContent: CharSequence
    ) {
        enableDisableHighlighting(content, editor, context, languageType, isBasicDisplayMode)
    }

    override fun afterDelete(
        content: Content,
        startLine: Int,
        startColumn: Int,
        endLine: Int,
        endColumn: Int,
        deletedContent: CharSequence
    ) {

    }
}

private fun enableDisableHighlighting(
    content: Content,
    editor: CodeEditor,
    context: Context,
    languageType: LanguageType,
    isBasicDisplayMode: Boolean
) {
    if (content.length > 10000000) {
        if (editor.editorLanguage::class != EmptyLanguage::class) {
            editor.setEditorLanguage(null)
            editor.isBasicDisplayMode = true
            Toast.makeText(
                context,
                "The file is too big. Syntax highlighting is disabled for performance reasons",
                Toast.LENGTH_LONG
            ).show()
        }
    } else {
        if (editor.editorLanguage::class == EmptyLanguage::class) {
            if (!isBasicDisplayMode) {
                editor.isBasicDisplayMode = false
            }
            val languageScopeName = languageType.code
            if (languageScopeName != null) {
                val language = TextMateLanguage.create(
                    languageScopeName, true
                )
                editor.setEditorLanguage(language)
            }
        }
    }
}