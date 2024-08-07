package com.yas.transaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.yas.model.ScreenState
import com.yas.new_request.NewRequestScreen
import com.yas.request.RequestScreen
import com.yas.request.RequestUiState
import com.yas.response.ResponseScreen
import com.yas.transaction.navigationDrawer.NavigationDrawer
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(
    screenState: ScreenState, navigateToEditor: () -> Unit, navigateToSettings: () -> Unit
) {


    val vm = koinViewModel<TransactionViewModel>()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        println("qqq")
        vm.saveRequest()
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                vm.saveRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            println("www")
            lifecycleOwner.lifecycle.removeObserver(observer)
            vm.saveRequest()
        }
    }

    NavigationDrawer(vm, navigateToSettings = { navigateToSettings() }) { drawerState ->
        Scaffold(topBar = {
            TransactionTopBar(vm = vm, drawerState = drawerState)
        }) {
            Surface(
                Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                val requestState = vm.requestState.collectAsState().value
                when (screenState) {
                    ScreenState.SINGLE_SCREEN -> {
                        Column {
                            val pagerState = rememberPagerState(pageCount = { 2 })
                            PrimaryTextTabs(pagerState = pagerState)
                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier.fillMaxWidth(),
                                userScrollEnabled = false
                            ) {
                                when (it) {
                                    0 -> {
                                        when (requestState) {
                                            RequestUiState.Loading -> {}
                                            RequestUiState.NewRequest -> {
                                                NewRequestScreen()
                                            }

                                            is RequestUiState.Success -> {
                                                RequestScreen(
                                                    modifier = Modifier.fillMaxSize(),
                                                    pagerState = pagerState,
                                                    navigateToEditor = navigateToEditor,
                                                    requestModel = requestState.request,
                                                    updateRequest = vm::updateRequest,
                                                    sendRequest = vm::sendRequest
                                                )
                                            }
                                        }
                                    }

                                    1 -> ResponseScreen(
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }

                    ScreenState.ROW_SCREEN -> {
                        Row {
                            when (requestState) {
                                RequestUiState.Loading -> {}
                                RequestUiState.NewRequest -> {
                                    NewRequestScreen()
                                }

                                is RequestUiState.Success -> {
                                    RequestScreen(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(1f),
                                        navigateToEditor = navigateToEditor,
                                        requestModel = requestState.request,
                                        updateRequest = vm::updateRequest,
                                        sendRequest = vm::sendRequest
                                    )
                                }
                            }

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
                            when (requestState) {
                                RequestUiState.Loading -> {}
                                RequestUiState.NewRequest -> {
                                    NewRequestScreen()
                                }

                                is RequestUiState.Success -> {
                                    RequestScreen(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(1f),
                                        navigateToEditor = navigateToEditor,
                                        requestModel = requestState.request,
                                        updateRequest = vm::updateRequest,
                                        sendRequest = vm::sendRequest
                                    )
                                }
                            }
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
        }
    }
}