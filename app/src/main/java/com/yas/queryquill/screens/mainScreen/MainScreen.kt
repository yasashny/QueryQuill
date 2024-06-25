package com.yas.queryquill.screens.mainScreen

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
import com.yas.domain.requestsDb.models.RequestModel
import com.yas.domain.sendRequest.ResponseModel
import com.yas.queryquill.adaptive.ScreenState
import com.yas.queryquill.components.PrimaryTextTabs
import com.yas.queryquill.screens.requestScreens.RequestScreen
import com.yas.queryquill.screens.requestScreens.viewModel.RequestEvent
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel
import com.yas.queryquill.screens.responseScreens.httpResponseScreen.HttpResponseScreen
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction1


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    screenState: ScreenState,
    requestModel: StateFlow<RequestModel>,
    requestState: StateFlow<RequestState>,
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    sendRequest: suspend (RequestModel) -> Unit,
    onEvent: KFunction1<RequestEvent, Unit>,
    responseState: StateFlow<ResponseModel>,
    navigateToEditor: () -> Unit
) {

    when (screenState) {
        ScreenState.SINGLE_SCREEN -> {
            Column {
                val pagerState = rememberPagerState(pageCount = { 2 })
                PrimaryTextTabs(pagerState = pagerState)
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    userScrollEnabled = false
                ) {
                    when (it) {
                        0 -> RequestScreen(
                            modifier = Modifier.fillMaxSize(),
                            requestModel = requestModel,
                            requestStateFlow = requestState,
                            updateRequest = updateRequest,
                            onEvent = onEvent,
                            sendRequest = sendRequest,
                            pagerState = pagerState,
                            navigateToEditor = navigateToEditor
                        )

                        1 -> HttpResponseScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            responseModelFlow = responseState
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
                    requestStateFlow = requestState,
                    updateRequest = updateRequest,
                    onEvent = onEvent,
                    sendRequest = sendRequest,
                    navigateToEditor = navigateToEditor
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
                        .weight(1f), responseModelFlow = responseState
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
                    requestStateFlow = requestState,
                    updateRequest = updateRequest,
                    onEvent = onEvent,
                    sendRequest = sendRequest,
                    navigateToEditor = navigateToEditor
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
                        .weight(1f), responseModelFlow = responseState
                )
            }
        }
    }
}