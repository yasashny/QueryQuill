package ru.yasdev.queryquill.screens.mainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.yasdev.queryquill.adaptive.ScreenState
import ru.yasdev.queryquill.components.FancyIndicatorContainerTabs
import ru.yasdev.queryquill.screens.httpRequestScreen.HttpRequestScreen
import ru.yasdev.queryquill.screens.httpRequestScreen.HttpRequestScreenViewModel
import ru.yasdev.queryquill.screens.httpResponseScreen.HttpResponseScreen
import ru.yasdev.queryquill.screens.httpResponseScreen.HttpResponseScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(screenState: ScreenState, requestVM: HttpRequestScreenViewModel, responseVM: HttpResponseScreenViewModel) {
    when (screenState) {
        ScreenState.SINGLE_SCREEN -> {
            Column {
                val tabsState = remember {
                    mutableStateOf(0)
                }
                val pagerState = rememberPagerState {
                    2
                }
                LaunchedEffect(key1 = tabsState.value){

                    pagerState.animateScrollToPage(tabsState.value)
                }
                LaunchedEffect(key1 = pagerState.currentPage, key2 = pagerState.isScrollInProgress){
                    if (!pagerState.isScrollInProgress){
                        tabsState.value = pagerState.currentPage
                    }


                }
                FancyIndicatorContainerTabs(tabsState)
                HorizontalPager(state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    when(it){
                        0 -> HttpRequestScreen(modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Blue),
                            httpRequestScreenViewModel = requestVM)
                        1 -> HttpResponseScreen(modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(Color.Red),
                            httpResponseScreenViewModel = responseVM)
                    }
                }
            }
        }

        ScreenState.ROW_SCREEN -> {
            Row{
                HttpRequestScreen(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
                    .weight(1f),
                    httpRequestScreenViewModel = requestVM)
                HttpResponseScreen(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color.Red)
                    .weight(1f),
                    httpResponseScreenViewModel = responseVM)
            }
        }

        ScreenState.COLUMN_SCREEN -> {
            Column {
                HttpRequestScreen(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
                    .weight(1f),
                    httpRequestScreenViewModel = requestVM)
                HttpResponseScreen(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(Color.Red)
                    .weight(1f),
                    httpResponseScreenViewModel = responseVM)
            }
        }

    }
}