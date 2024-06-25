package com.yas.queryquill.screens.requestScreens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.domain.requestsDb.states.BodyState
import com.yas.queryquill.components.codeEditor.CodeEditor
import com.yas.queryquill.components.codeEditor.CodeEditorState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel
import kotlinx.coroutines.flow.StateFlow


@Composable
fun RequestCodeEditorScreen(
    requestModelFlow: StateFlow<RequestModel>, updateRequest: (UpdateHttpRequestModel) -> Unit
) {

    val requestModel by requestModelFlow.collectAsState()

    val bodyState = requestModel.bodyState as BodyState.Text

    val editorState = CodeEditorState()
    editorState.initialContent.insert(0, 0, bodyState.text)
    CodeEditor(state = editorState, modifier = Modifier.fillMaxSize()) { newText ->
        updateRequest(UpdateHttpRequestModel.Body(BodyState.Text(newText, bodyState.textType)))
    }

}