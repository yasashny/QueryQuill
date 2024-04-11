package ru.yasdev.queryquill.screens.requestScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.HttpRequestScreen
import ru.yasdev.queryquill.screens.requestScreens.loadingScreen.LoadingScreen
import ru.yasdev.queryquill.screens.requestScreens.newRequestScreen.NewRequestScreen
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestEvent
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestState
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel

@Composable
fun RequestScreen(
    modifier: Modifier,
    requestModel: RequestModel,
    requestState: RequestState,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
    onEvent: (RequestEvent) -> Unit
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
                HttpRequestScreen(requestModel = requestModel, updateRequest = updateRequest)
            }
        }
    }
}