package com.yas.queryquill.screens.requestScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.domain.requestsDb.states.BodyState
import com.yas.queryquill.components.codeEditor.CodeEditor
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel
import kotlinx.coroutines.flow.StateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RequestCodeEditorScreen(
    requestModelFlow: StateFlow<RequestModel>, updateRequest: (UpdateHttpRequestModel) -> Unit
) {

    val bodyState = requestModelFlow.value.bodyState as BodyState.Text

    CodeEditor(initialText = bodyState.text, modifier = Modifier.fillMaxSize()) { newText ->
        updateRequest(UpdateHttpRequestModel.Body(BodyState.Text(newText, bodyState.textType)))
    }

}