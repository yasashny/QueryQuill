package ru.yasdev.queryquill.screens.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.sendRequest.ResponseModel
import ru.yasdev.queryquill.adaptive.ScreenState
import ru.yasdev.queryquill.components.PrimaryTextTabs
import ru.yasdev.queryquill.screens.requestScreens.RequestScreen
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestEvent
import ru.yasdev.queryquill.screens.requestScreens.viewModel.RequestState
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel
import ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen.HttpResponseScreen
import kotlin.reflect.KFunction1

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    screenState: ScreenState,
    requestModel: RequestModel,
    requestState: RequestState,
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    sendRequest: suspend (RequestModel) -> Unit,
    onEvent: KFunction1<RequestEvent, Unit>,
    responseState: ResponseModel
) {
    when (screenState) {
        ScreenState.SINGLE_SCREEN -> {
            Column {
                val pagerState = rememberPagerState(pageCount = { 2 })
                PrimaryTextTabs(pagerState = pagerState)
                HorizontalPager(
                    state = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    userScrollEnabled = false
                ) {
                    when (it) {
                        0 -> RequestScreen(
                            modifier = Modifier.fillMaxSize(),
                            requestModel = requestModel,
                            requestState = requestState,
                            updateRequest = updateRequest,
                            onEvent = onEvent,
                            sendRequest = sendRequest,
                            pagerState = pagerState
                        )

                        1 -> HttpResponseScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            responseModel = responseState
                        )
                    }
                }
            }
        }

        ScreenState.ROW_SCREEN -> {
            Row {
                RequestScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    requestModel = requestModel,
                    requestState = requestState,
                    updateRequest = updateRequest,
                    onEvent = onEvent,
                    sendRequest = sendRequest
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                HttpResponseScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    responseModel = responseState
                )
            }
        }

        ScreenState.COLUMN_SCREEN -> {
            Column {
                RequestScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    requestModel = requestModel,
                    requestState = requestState,
                    updateRequest = updateRequest,
                    onEvent = onEvent,
                    sendRequest = sendRequest
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                HttpResponseScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    responseModel = responseState

                )
            }
        }
    }
}