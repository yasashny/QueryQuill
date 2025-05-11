/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.model.ScreenState
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.transaction.navigationDrawer.NavigationDrawer
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState
import org.queryquill.app.feature.transaction.util.TestTags

@Composable
fun TransactionScreen(
    screenState: ScreenState,
    navigateToEditor: (fileName: String, textType: TextType) -> Unit,
    navigateToSettings: @Composable (() -> Unit) -> Unit,
    navigateToCookie: () -> Unit,
    navigateToRequestScreen: @Composable (
        modifier: Modifier, navigateToEditor: (fileName: String, textType: TextType) -> Unit, onRequestSent: () -> Unit
    ) -> Unit,
    goToResponseScreen: @Composable (modifier: Modifier) -> Unit,
    openAddTransactionDialog: @Composable (() -> Unit) -> Unit,
    goToNewTransactionScreen: @Composable () -> Unit
) {
    val vm = koinViewModel<TransactionViewModel>()
    val transactions = vm.transactions.collectAsStateWithLifecycle().value

    TransactionScreen(
        screenState = screenState,
        navigateToEditor = navigateToEditor,
        navigateToSettings = navigateToSettings,
        navigateToCookie = navigateToCookie,
        navigateToRequestScreen = navigateToRequestScreen,
        goToResponseScreen = goToResponseScreen,
        openAddTransactionDialog = openAddTransactionDialog,
        goToNewTransactionScreen = goToNewTransactionScreen,
        transactions = transactions,
        onEvent = vm::onEvent,
    )
}

@Composable
internal fun TransactionScreen(
    screenState: ScreenState,
    navigateToEditor: (fileName: String, textType: TextType) -> Unit,
    navigateToSettings: @Composable (() -> Unit) -> Unit,
    navigateToCookie: () -> Unit,
    navigateToRequestScreen: @Composable (
        modifier: Modifier, navigateToEditor: (fileName: String, textType: TextType) -> Unit, onRequestSent: () -> Unit
    ) -> Unit,
    goToResponseScreen: @Composable (modifier: Modifier) -> Unit,
    openAddTransactionDialog: @Composable (() -> Unit) -> Unit,
    goToNewTransactionScreen: @Composable () -> Unit,
    transactions: TransactionsUiState,
    onEvent: (TransactionEvent) -> Unit
) {

    var openSettings by remember {
        mutableStateOf(false)
    }
    if (openSettings) {
        navigateToSettings {
            openSettings = false
        }
    }
    var currentId: Long? by remember {
        mutableStateOf(null)
    }

    NavigationDrawer(
        transactions = transactions,
        navigateToSettings = { openSettings = true },
        navigateToCookie = { navigateToCookie() },
        onEvent = onEvent,
        addTransactionDialog = openAddTransactionDialog
    ) { drawerState ->
        Scaffold(topBar = {
            TransactionTopBar(
                transactions = transactions,
                drawerState = drawerState,
                updateTransaction = { newTransaction ->
                    onEvent(TransactionEvent.UpdateTransaction(newTransaction))
                })
        }) { paddingValues ->
            Surface(
                Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                when (transactions) {
                    TransactionsUiState.Loading -> {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .testTag(TestTags.TransactionScreen.LOADING_STATE),
                            contentAlignment = Alignment.Center
                        ) {

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
                                            Column(modifier = Modifier.testTag(TestTags.TransactionScreen.SINGLE_SCREEN)) {
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
                                            Row(modifier = Modifier.testTag(TestTags.TransactionScreen.ROW_SCREEN)) {
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
                                            Column(modifier = Modifier.testTag(TestTags.TransactionScreen.COLUMN_SCREEN)) {
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

@Preview(showBackground = true)
@Composable
private fun PreviewTransactionScreen() {
    TransactionScreen(
        screenState = ScreenState.SINGLE_SCREEN,
        navigateToEditor = { _, _ -> },
        navigateToSettings = {},
        navigateToCookie = { },
        navigateToRequestScreen = { _, _, _ -> },
        goToResponseScreen = {},
        openAddTransactionDialog = {},
        goToNewTransactionScreen = {},
        transactions = TransactionsUiState.Success(emptyList(), currentId = 1L),
        onEvent = {})
}