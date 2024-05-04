package com.yas.queryquill.screens.requestScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.queryquill.screens.requestScreens.httpRequestScreen.HttpRequestScreen
import com.yas.queryquill.screens.requestScreens.loadingScreen.LoadingScreen
import com.yas.queryquill.screens.requestScreens.newRequestScreen.NewRequestScreen
import com.yas.queryquill.screens.requestScreens.viewModel.RequestEvent
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RequestScreen(
    modifier: Modifier,
    requestModel: RequestModel,
    requestState: RequestState,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
    onEvent: (RequestEvent) -> Unit,
    sendRequest: suspend (RequestModel) -> Unit,
    pagerState: PagerState? = null
) {
    Box(modifier = modifier) {
        when (requestState) {
            RequestState.Loading -> {
                LoadingScreen()
            }

            RequestState.Null -> {
                NewRequestScreen { addRequestModel ->
                    onEvent(RequestEvent.AddRequest(addRequestModel))
                }
            }

            RequestState.Request -> {
                HttpRequestScreen(requestModel = requestModel, updateRequest = updateRequest, sendRequest = sendRequest, pagerState = pagerState)
            }
        }
    }
}