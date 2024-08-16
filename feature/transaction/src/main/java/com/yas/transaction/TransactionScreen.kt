package com.yas.transaction

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.yas.model.RequestModel
import com.yas.model.ScreenState
import com.yas.new_transaction.NewRequestScreen
import com.yas.request.RequestScreen
import com.yas.response.ResponseScreen
import com.yas.transaction.navigationDrawer.NavigationDrawer
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionScreen(
    screenState: ScreenState,
    navigateToEditor: (textFileName: String, languageType: String) -> Unit,
    navigateToSettings: () -> Unit
) {

    val vm = koinViewModel<TransactionViewModel>()

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                vm.saveRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val transactions = vm.transactions.collectAsState().value

    NavigationDrawer(
        transactions = transactions,
        navigateToSettings = { navigateToSettings() },
        onEvent = vm::onEvent
    ) { drawerState ->
        Scaffold(topBar = {
            TransactionTopBar(transactions = transactions,
                drawerState = drawerState,
                updateTransaction = { newTransaction ->
                    vm.onEvent(TransactionEvent.UpdateTransaction(newTransaction))
                })
        }) { paddingValues ->
            Surface(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                val requestState = vm.requestState.collectAsState().value
                val responseModel = vm.responseModel.collectAsState().value
                when (requestState) {
                    RequestUiState.Loading -> {}
                    RequestUiState.NewRequest -> {
                        NewRequestScreen()
                    }

                    is RequestUiState.Success -> {
                        when (screenState) {
                            ScreenState.SINGLE_SCREEN -> {
                                Column {
                                    val tabsScreenState = remember {
                                        mutableStateOf(TabsScreenState.REQUEST)
                                    }
                                    PrimaryTextTabs(tabsScreenState)
                                    when (tabsScreenState.value) {
                                        TabsScreenState.REQUEST -> {
                                            RequestScreen(modifier = Modifier.fillMaxSize(),
                                                navigateToEditor = navigateToEditor,
                                                requestModel = requestState.request,
                                                updateRequest = vm::updateRequest,
                                                getTextFileUri = vm::getFileUriByName,
                                                sendRequest = { requestModel: RequestModel, requestSent: () -> Unit ->
                                                    vm.sendRequest(requestModel) {
                                                        requestSent()
                                                        tabsScreenState.value =
                                                            TabsScreenState.RESPONSE
                                                    }
                                                })
                                        }

                                        TabsScreenState.RESPONSE -> {
                                            ResponseScreen(
                                                modifier = Modifier.fillMaxSize(), responseModel,
                                                vm::getFileUriByName
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
                                        navigateToEditor = navigateToEditor,
                                        requestModel = requestState.request,
                                        updateRequest = vm::updateRequest,
                                        getTextFileUri = vm::getFileUriByName,
                                        sendRequest = vm::sendRequest
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
                                            .weight(1f), responseModel,
                                        vm::getFileUriByName
                                    )
                                }
                            }

                            ScreenState.COLUMN_SCREEN -> {
                                Column {
                                    RequestScreen(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(1f),
                                        navigateToEditor = navigateToEditor,
                                        requestModel = requestState.request,
                                        updateRequest = vm::updateRequest,
                                        getTextFileUri = vm::getFileUriByName,
                                        sendRequest = vm::sendRequest
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
                                            .weight(1f), responseModel,
                                        vm::getFileUriByName
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}