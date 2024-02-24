package ru.yasdev.queryquill.screens.requestScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.states.RequestState
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.RequestEvent
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.HttpRequestScreen
import kotlin.reflect.KFunction1

@Composable
fun RequestScreen(
    modifier: Modifier,
    requestModel: RequestModel,
    requestState: RequestState,
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    onEvent: KFunction1<RequestEvent, Unit>
) {

    Box(modifier = modifier) {

        when (requestState) {
            RequestState.Loading -> {
                Text(text = "Loadingggg")
            }

            RequestState.Null -> {
                NewRequestScreen(onEvent)
            }

            RequestState.Request -> {
                HttpRequestScreen(requestModel = requestModel, updateRequest = updateRequest)
            }

            else -> {
                Text(text = "error")
            }
        }
    }


}