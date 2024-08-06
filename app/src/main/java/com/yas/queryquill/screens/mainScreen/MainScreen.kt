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
import com.yas.model.RequestModel
import com.yas.queryquill.adaptive.ScreenState
import com.yas.queryquill.components.PrimaryTextTabs
import com.yas.queryquill.screens.requestScreens.RequestScreen
import com.yas.queryquill.screens.requestScreens.viewModel.RequestEvent
import com.yas.queryquill.screens.requestScreens.viewModel.RequestState
import com.yas.queryquill.screens.requestScreens.viewModel.UpdateRequestModel
import com.yas.response.ResponseScreen
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction1


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    screenState: ScreenState,
    requestState: StateFlow<RequestState>,
    updateRequest: KFunction1<UpdateRequestModel, Unit>,
    sendRequest: suspend (RequestModel) -> Unit,
    onEvent: KFunction1<RequestEvent, Unit>,
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
                        .fillMaxWidth(),
                    userScrollEnabled = false
                ) {
                    when (it) {
                        0 -> RequestScreen(
                            modifier = Modifier.fillMaxSize(),
                            requestStateFlow = requestState,
                            updateRequest = updateRequest,
                            onEvent = onEvent,
                            sendRequest = sendRequest,
                            pagerState = pagerState,
                            navigateToEditor = navigateToEditor
                        )

                        1 -> ResponseScreen(
                            modifier = Modifier
                                .fillMaxSize()
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
                ResponseScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        }

        ScreenState.COLUMN_SCREEN -> {
            Column {
                RequestScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
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
                ResponseScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        }
    }
}