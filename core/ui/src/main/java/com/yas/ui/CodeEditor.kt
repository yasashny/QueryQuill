package com.yas.ui

import android.content.Context
import android.graphics.Typeface
import android.view.ViewGroup
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
import com.yas.model.CodeEditorState
import com.yas.model.LanguageType
import io.github.rosemoe.sora.langs.textmate.TextMateColorScheme
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.widget.CodeEditor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream

@Composable
fun CodeEditor(
    modifier: Modifier = Modifier,
    state: CodeEditorState,
    isEditable: Boolean = true,
    isBasicDisplayMode: Boolean,
    languageType: LanguageType,
    file: File
) {

    val context = LocalContext.current
    val inputStream = file.inputStream()
    var isLoading by remember {
        mutableStateOf(true)
    }
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
                isBasicDisplayMode = isBasicDisplayMode
            )
        }, modifier = modifier, onRelease = {
            it.release()
        }, update = {
            it.setText(state.content)
        })
    }
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

internal fun InputStream.readInChunks(chunkSize: Int = 100): Sequence<String> = sequence {
    val buffer = ByteArray(chunkSize)
    var bytesRead: Int

    while (this@readInChunks.read(buffer).also { bytesRead = it } != -1) {
        yield(String(buffer, 0, bytesRead))
    }
}