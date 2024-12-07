package org.queryquill.app.feature.request_code_editor

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.core.ui.CodeEditor
import org.queryquill.app.core.ui.QueryQuillTopBar
import org.queryquill.app.core.ui.rememberCodeEditorState
import java.io.File


@Composable
fun RequestCodeEditorScreen(
    textFileName: String, stringLanguageType: String, navigateUp: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val languageType = when (stringLanguageType) {
        "Json" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.JSON
        } else {
            LanguageType.OTHER
        }

        "Xml" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LanguageType.XML
        } else {
            LanguageType.OTHER
        }

        "Plain" -> LanguageType.PLAIN
        "Other" -> LanguageType.OTHER
        else -> {
            LanguageType.OTHER
        }
    }

    Scaffold(topBar = {
        QueryQuillTopBar(title = {
            Text(
                text = "Text/${stringLanguageType}"
            )
        }, navigationIcon = {
            TextButton(onClick = {
                keyboardController?.hide()
                navigateUp()
            }) {
                Text(text = stringResource(R.string.done))
            }
        })
    }) { paddingValues ->
        Surface(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val ctx = LocalContext.current
            val file = File(ctx.filesDir, textFileName)
            if (!file.exists()) {
                file.writeText("")
            }

            val state = rememberCodeEditorState()
            CodeEditor(
                state = state, isBasicDisplayMode = false, languageType = languageType, file = file
            )

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