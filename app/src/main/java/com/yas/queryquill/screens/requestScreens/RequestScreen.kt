package com.yas.queryquill.screens.requestScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.queryquill.screens.requestScreens.httpRequestScreen.HttpRequestScreen
import com.yas.queryquill.screens.requestScreens.loadingScreen.LoadingScreen
import com.yas.queryquill.screens.requestScreens.newRequestScreen.NewRequestScreen
import com.yas.queryquill.screens.requestScreens.viewModel.RequestEvent
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RequestScreen(
    modifier: Modifier,
    requestStateFlow: StateFlow<RequestState>,
    updateRequest: (UpdateRequestModel) -> Unit,
    onEvent: (RequestEvent) -> Unit,
    sendRequest: suspend (RequestModel) -> Unit,
    pagerState: PagerState? = null,
    navigateToEditor: () -> Unit
) {
    val requestState by requestStateFlow.collectAsState()

    Box(modifier = modifier) {
        when (val state = requestState) {
            RequestState.Loading -> {
                LoadingScreen()
            }

            RequestState.NewRequest -> {
                NewRequestScreen { addRequestModel ->
                    onEvent(RequestEvent.AddRequest(addRequestModel))
                }
            }

            is RequestState.Request -> {
                HttpRequestScreen(
                    requestModel = state.request,
                    updateRequest = updateRequest,
                    sendRequest = sendRequest,
                    pagerState = pagerState,
                    navigateToEditor = navigateToEditor
                )
            }
        }
    }
}