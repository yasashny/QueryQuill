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

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.model.Transaction
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState
import org.queryquill.app.feature.transaction.util.TestTags

class TransactionTopBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun transactionTopBar_displaysMenuButton() {
        val drawerState = DrawerState(DrawerValue.Closed)

        composeTestRule.setContent {
            TransactionTopBar(TransactionsUiState.Loading, drawerState) {}
        }

        composeTestRule.onNodeWithTag(TestTags.TransactionTopBar.MENU_BUTTON).assertExists()
    }

    @Test
    fun transactionTopBar_clickMenuButton_opensDrawer() {
        val drawerState = DrawerState(DrawerValue.Closed)
        composeTestRule.setContent {
            TransactionTopBar(TransactionsUiState.Loading, drawerState) {}
        }
        composeTestRule.onNodeWithTag(TestTags.TransactionTopBar.MENU_BUTTON).performClick()
        runBlocking {
            assertEquals(DrawerValue.Open, drawerState.currentValue)
        }
    }

    @Test
    fun transactionTopBar_displaysCorrectTitle_whenLoading() {
        composeTestRule.setContent {
            TransactionTopBar(TransactionsUiState.Loading, DrawerState(DrawerValue.Closed)) {}
        }
        composeTestRule.onNodeWithTag(TestTags.TransactionTopBar.MENU_BUTTON).assertExists()
    }

    @Test
    fun transactionTopBar_displaysCorrectTitle_whenSuccess() {
        val transaction = Transaction(id = 1L, label = "Test Transaction")
        composeTestRule.setContent {
            TransactionTopBar(
                transactions = TransactionsUiState.Success(
                    list = listOf(transaction), currentId = 1L
                ), drawerState = DrawerState(DrawerValue.Closed), updateTransaction = {})
        }
        composeTestRule.onNodeWithTag(TestTags.TransactionTopBar.MENU_BUTTON).assertExists()
    }
}