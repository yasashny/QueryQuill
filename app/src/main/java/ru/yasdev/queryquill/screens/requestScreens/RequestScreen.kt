package ru.yasdev.queryquill.screens.requestScreens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.HttpRequestScreen
import ru.yasdev.queryquill.screens.requestScreens.loadingScreen.LoadingScreen
import ru.yasdev.queryquill.screens.requestScreens.newRequestScreen.NewRequestScreen
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestEvent
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestState
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel

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