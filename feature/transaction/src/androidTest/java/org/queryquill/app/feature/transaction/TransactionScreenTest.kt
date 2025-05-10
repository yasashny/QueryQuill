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

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.model.ScreenState
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState
import org.queryquill.app.feature.transaction.util.TestTags

class TransactionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun transactionScreen_displaysLoadingState_whenStateIsLoading() {
        composeTestRule.setContent {
            TestTransactionScreen(
                transactions = TransactionsUiState.Loading
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TransactionScreen.LOADING_STATE).assertIsDisplayed()
    }

    @Test
    fun transactionScreen_displaysSingleScreen_whenStateIsSingleScreenAndDataExists() {
        composeTestRule.setContent {
            TestTransactionScreen(
                screenState = ScreenState.SINGLE_SCREEN, transactions = TransactionsUiState.Success(
                    list = listOf(), currentId = 1L
                )
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TransactionScreen.SINGLE_SCREEN).assertIsDisplayed()
    }

    @Test
    fun transactionScreen_displaysRowScreen_whenStateIsRowScreenAndDataExists() {
        composeTestRule.setContent {
            TestTransactionScreen(
                screenState = ScreenState.ROW_SCREEN, transactions = TransactionsUiState.Success(
                    list = listOf(), currentId = 1L
                )
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TransactionScreen.ROW_SCREEN).assertIsDisplayed()
    }

    @Test
    fun transactionScreen_displaysColumnScreen_whenStateIsColumnScreenAndDataExists() {
        composeTestRule.setContent {
            TestTransactionScreen(
                screenState = ScreenState.COLUMN_SCREEN, transactions = TransactionsUiState.Success(
                    list = listOf(), currentId = 1L
                )
            )
        }
        composeTestRule.onNodeWithTag(TestTags.TransactionScreen.COLUMN_SCREEN).assertIsDisplayed()
    }

    @Composable
    private fun TestTransactionScreen(
        screenState: ScreenState = ScreenState.SINGLE_SCREEN,
        transactions: TransactionsUiState = TransactionsUiState.Loading
    ) {
        TransactionScreen(
            screenState = screenState,
            navigateToEditor = { _, _ -> },
            navigateToSettings = {},
            navigateToCookie = {},
            navigateToRequestScreen = { _, _, _ -> },
            goToResponseScreen = {},
            openAddTransactionDialog = {},
            goToNewTransactionScreen = {},
            transactions = transactions,
            onEvent = {})
    }
}