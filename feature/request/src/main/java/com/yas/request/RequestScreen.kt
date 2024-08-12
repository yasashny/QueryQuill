package com.yas.request

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.model.RequestModel

@Composable
fun RequestScreen(
    modifier: Modifier,
    navigateToEditor: () -> Unit,
    requestModel: RequestModel,
    updateRequest: (UpdateRequestModel) -> Unit,
    sendRequest: (RequestModel, () -> Unit) -> Unit
) {
    Box(modifier = modifier) {
        HttpRequestScreen(
            requestModel = requestModel,
            updateRequest = updateRequest,
            sendRequest = sendRequest,
            navigateToEditor = navigateToEditor
        )
    }
}