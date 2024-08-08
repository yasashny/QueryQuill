package com.yas.request

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.model.RequestModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RequestScreen(
    modifier: Modifier,
    pagerState: PagerState? = null,
    navigateToEditor: () -> Unit,
    requestModel: RequestModel,
    updateRequest: (UpdateRequestModel) -> Unit,
    sendRequest: suspend (RequestModel) -> Unit
) {
    Box(modifier = modifier) {
        HttpRequestScreen(
            requestModel = requestModel,
            updateRequest = updateRequest,
            sendRequest = sendRequest,
            pagerState = pagerState,
            navigateToEditor = navigateToEditor
        )
    }
}