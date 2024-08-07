package com.yas.request_code_editor

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yas.model.BodyState
import com.yas.model.RequestModel
import com.yas.ui.CodeEditor
import com.yas.ui.QueryQuillTopBar
import io.github.rosemoe.sora.text.Content
import org.koin.androidx.compose.koinViewModel


@Composable
fun RequestCodeEditorScreen(navigateUp: () -> Unit) {

    val vm = koinViewModel<RequestCodeEditorViewModel>()

    val requestModel = vm.requestModel.collectAsState().value

    Scaffold(topBar = {
        QueryQuillTopBar(title = {
            Text(
                text = when (requestModel) {
                    null -> ""
                    else -> {
                        when (val state = requestModel.bodyState) {
                            is BodyState.BinaryFile -> ""
                            is BodyState.FormUrlEncoded -> ""
                            is BodyState.MultipartForm -> ""
                            BodyState.NoBody -> ""
                            is BodyState.Text -> "Text/${state.textType.title}"
                        }
                    }
                }
            )
        }, navigationIcon = {
            TextButton(onClick = { navigateUp() }) {
                Text(text = "Done")
            }
        })
    }) {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (requestModel) {
                null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    when (val bodyState = requestModel.bodyState) {
                        is BodyState.BinaryFile -> {}
                        is BodyState.FormUrlEncoded -> {}
                        is BodyState.MultipartForm -> {}
                        BodyState.NoBody -> {}
                        is BodyState.Text -> {
                            CodeEditor(
                                initialText = bodyState.text,
                                modifier = Modifier.fillMaxSize(),
                                languageType = textTypeToLanguageName(bodyState.textType),
                                isBasicDisplayMode = false
                            ) { newText ->
                                vm.update(newText)
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
    }
}