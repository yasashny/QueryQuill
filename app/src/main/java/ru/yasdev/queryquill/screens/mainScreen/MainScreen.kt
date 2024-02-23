package ru.yasdev.queryquill.screens.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.adaptive.ScreenState
import ru.yasdev.queryquill.components.PrimaryTextTabs
import ru.yasdev.queryquill.screens.requestScreens.RequestScreen
import ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen.HttpResponseScreen
import ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen.HttpResponseScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    screenState: ScreenState,
    viewModel: MainActivityViewModel,
    responseVM: HttpResponseScreenViewModel
) {

    when (screenState) {
        ScreenState.SINGLE_SCREEN -> {
            Column {
                val pagerState = rememberPagerState(pageCount = { 2 })
                PrimaryTextTabs(pagerState = pagerState)
                HorizontalPager(
                    state = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    when (it) {
                        0 -> RequestScreen(
                            modifier = Modifier.fillMaxSize(), viewModel = viewModel
                        )

                        1 -> HttpResponseScreen(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            httpResponseScreenViewModel = responseVM
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
                        .weight(1f), viewModel = viewModel
                )
                HttpResponseScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .weight(1f),
                    httpResponseScreenViewModel = responseVM
                )
            }
        }

        ScreenState.COLUMN_SCREEN -> {
            Column {
                RequestScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f), viewModel = viewModel
                )
                HttpResponseScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .weight(1f),
                    httpResponseScreenViewModel = responseVM
                )
            }
        }

    }
}