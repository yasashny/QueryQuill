package com.yas.request_code_editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import com.yas.model.CodeEditorState
import com.yas.model.LanguageType
import com.yas.ui.CodeEditor
import com.yas.ui.QueryQuillTopBar
import com.yas.ui.rememberCodeEditorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.io.InputStream


@Composable
fun RequestCodeEditorScreen(
    textFileName: String, stringLanguageType: String, navigateUp: () -> Unit
) {

    val languageType = when (stringLanguageType) {
        "Json" -> LanguageType.JSON
        "Xml" -> LanguageType.XML
        "Plain" -> LanguageType.PLAIN
        "Other" -> LanguageType.OTHER
        else -> {
            LanguageType.OTHER
        }
    }

    val vm = koinViewModel<RequestCodeEditorViewModel>()

    var isLoading by remember {
        mutableStateOf(true)
    }

    Scaffold(topBar = {
        QueryQuillTopBar(title = {
            Text(
                text = "Text/${stringLanguageType}"
            )
        }, navigationIcon = {
            TextButton(onClick = { navigateUp() }) {
                Text(text = stringResource(R.string.done))
            }
        })
    }) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val file = File(vm.getTextFileUri(textFileName))
            val inputStream = file.inputStream()

            val state = rememberCodeEditorState()

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                CodeEditor(state = state, isBasicDisplayMode = false, languageType = languageType)
            }

            LaunchedEffect(Unit) {
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

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        lifecycleOwner.lifecycleScope.launch {
                            saveFile(file, state)
                        }
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
        }
    }
}

internal fun InputStream.readInChunks(chunkSize: Int = 100): Sequence<String> = sequence {
    val buffer = ByteArray(chunkSize)
    var bytesRead: Int

    while (this@readInChunks.read(buffer).also { bytesRead = it } != -1) {
        yield(String(buffer, 0, bytesRead))
    }
}

internal fun saveFile(file: File, state: CodeEditorState) {
    file.writeText("")
    var start = 0
    val end = state.content.length
    while (start < end) {
        file.appendBytes(
            state.content.substring(
                start, if (start + 10000 > end) end else start + 10000
            ).toByteArray()
        )
        start += 10000
    }
}