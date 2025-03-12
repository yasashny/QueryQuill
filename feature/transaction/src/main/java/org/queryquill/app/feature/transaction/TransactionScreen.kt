package org.queryquill.app.feature.transaction

import androidx.compose.animation.Crossfade
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.model.ScreenState
import org.queryquill.app.feature.transaction.navigationDrawer.NavigationDrawer
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState

@Composable
fun TransactionScreen(
    screenState: ScreenState,
    navigateToEditor: (textFileName: String, languageType: String) -> Unit,
    navigateToSettings: @Composable (() -> Unit) -> Unit,
    navigateToCookie: () -> Unit,
    navigateToRequestScreen: @Composable (
        modifier: Modifier, navigateToEditor: (textFileName: String, languageType: String) -> Unit, onRequestSent: () -> Unit
    ) -> Unit,
    goToResponseScreen: @Composable (modifier: Modifier) -> Unit,
    openAddTransactionDialog: @Composable (() -> Unit) -> Unit,
    goToNewTransactionScreen: @Composable () -> Unit
) {

    var openSettings by remember {
        mutableStateOf(false)
    }
    if (openSettings) {
        navigateToSettings {
            openSettings = false
        }
    }

    val vm = koinViewModel<TransactionViewModel>()
    var currentId: Long? by remember {
        mutableStateOf(null)
    }
    val transactions = vm.transactions.collectAsState().value


    NavigationDrawer(transactions = transactions,
        navigateToSettings = { openSettings = true },
        navigateToCookie = { navigateToCookie() },
        onEvent = vm::onEvent,
        addTransactionDialog = openAddTransactionDialog
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
                when (transactions) {
                    TransactionsUiState.Loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        }
                    }

                    is TransactionsUiState.Success -> {

                        currentId = transactions.currentId

                        Crossfade(targetState = currentId, label = "transactions") { cId ->
                            when (cId) {
                                null -> {
                                    goToNewTransactionScreen()
                                }

                                else -> {
                                    when (screenState) {
                                        ScreenState.SINGLE_SCREEN -> {
                                            Column {
                                                val tabsScreenState = remember {
                                                    mutableStateOf(TabsScreenState.REQUEST)
                                                }
                                                PrimaryTextTabs(tabsScreenState)
                                                Crossfade(
                                                    targetState = tabsScreenState.value,
                                                    label = "tabs"
                                                ) { screenState ->


                                                    when (screenState) {
                                                        TabsScreenState.REQUEST -> {
                                                            navigateToRequestScreen(
                                                                Modifier.fillMaxSize(),
                                                                navigateToEditor
                                                            ) {
                                                                tabsScreenState.value =
                                                                    TabsScreenState.RESPONSE
                                                            }
                                                        }

                                                        TabsScreenState.RESPONSE -> {
                                                            goToResponseScreen(
                                                                Modifier.fillMaxSize()
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        ScreenState.ROW_SCREEN -> {
                                            Row {
                                                navigateToRequestScreen(
                                                    Modifier
                                                        .fillMaxSize()
                                                        .weight(1f),
                                                    navigateToEditor
                                                ) {}
                                                Box(
                                                    Modifier
                                                        .fillMaxHeight()
                                                        .width(1.dp)
                                                        .background(MaterialTheme.colorScheme.outlineVariant)
                                                )
                                                goToResponseScreen(
                                                    Modifier
                                                        .fillMaxSize()
                                                        .weight(1f)
                                                )
                                            }
                                        }

                                        ScreenState.COLUMN_SCREEN -> {
                                            Column {
                                                navigateToRequestScreen(
                                                    Modifier
                                                        .fillMaxSize()
                                                        .weight(1f),
                                                    navigateToEditor
                                                ) {}
                                                Box(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .height(1.dp)
                                                        .background(MaterialTheme.colorScheme.outlineVariant)
                                                )
                                                goToResponseScreen(
                                                    Modifier
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
                }
            }
        }
    }
}