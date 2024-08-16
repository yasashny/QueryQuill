package com.yas.request

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.model.RequestModel
import com.yas.model.UpdateRequestModel
import java.net.URI

@Composable
fun RequestScreen(
    modifier: Modifier,
    navigateToEditor: (textFileName: String, languageType: String) -> Unit,
    requestModel: RequestModel,
    getTextFileUri: (textFileName: String) -> URI,
    updateRequest: (UpdateRequestModel) -> Unit,
    sendRequest: (RequestModel, () -> Unit) -> Unit
) {
    Box(modifier = modifier) {
        HttpRequestScreen(
            requestModel = requestModel,
            updateRequest = updateRequest,
            sendRequest = sendRequest,
            navigateToEditor = navigateToEditor,
            getTextFileUri = getTextFileUri
        )
    }
}