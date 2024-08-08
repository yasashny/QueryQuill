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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yas.model.LanguageType
import com.yas.ui.CodeEditor
import com.yas.ui.QueryQuillTopBar
import org.koin.androidx.compose.koinViewModel


@Composable
fun RequestCodeEditorScreen(navigateUp: () -> Unit) {

    val vm = koinViewModel<RequestCodeEditorViewModel>()

    val requestCodeEditorUiState = vm.requestCodeEditorUiState.collectAsState().value

    Scaffold(topBar = {
        QueryQuillTopBar(title = {
            Text(
                text = when (requestCodeEditorUiState) {
                    RequestCodeEditorUiState.Loading -> {
                        ""
                    }

                    is RequestCodeEditorUiState.Success -> {
                        "Text/${requestCodeEditorUiState.bodyState.textType.title}"
                    }
                }
            )
        }, navigationIcon = {
            TextButton(onClick = { navigateUp() }) {
                Text(text = stringResource(R.string.done))
            }
        })
    }) {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (requestCodeEditorUiState) {

                RequestCodeEditorUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CodeEditor(
                            initialText = "",
                            isBasicDisplayMode = false,
                            languageType = LanguageType.PLAIN,
                            isEditable = false
                        )
                        CircularProgressIndicator()
                    }
                }

                is RequestCodeEditorUiState.Success -> {
                    CodeEditor(
                        initialText = requestCodeEditorUiState.bodyState.text,
                        modifier = Modifier.fillMaxSize(),
                        languageType = textTypeToLanguageName(requestCodeEditorUiState.bodyState.textType),
                        isBasicDisplayMode = false
                    ) { newText ->
                        vm.updateText(newText)
                    }
                    DisposableEffect(Unit) {
                        onDispose {
                            vm.saveBody()
                        }
                    }
                }
            }
        }
    }
}