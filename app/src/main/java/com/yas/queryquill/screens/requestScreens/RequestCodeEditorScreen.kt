package com.yas.queryquill.screens.requestScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.domain.requestsDb.states.BodyState
import com.yas.queryquill.components.codeEditor.CodeEditor
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel
import com.yas.queryquill.utils.textTypeToLanguageName
import kotlinx.coroutines.flow.StateFlow


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RequestCodeEditorScreen(
    requestModelFlow: StateFlow<RequestState>, updateRequest: (UpdateRequestModel) -> Unit
) {

    val bodyState = ((requestModelFlow.value as RequestState.Request).request.bodyState as BodyState.Text)

    CodeEditor(
        initialText = bodyState.text,
        modifier = Modifier.fillMaxSize(),
        languageType = textTypeToLanguageName(bodyState.textType),
        isBasicDisplayMode = false
    ) { newText ->
        updateRequest(UpdateRequestModel.Body(BodyState.Text(newText, bodyState.textType)))
    }

}