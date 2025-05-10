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

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.queryquill.app.core.model.GetTransactionModel
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.Transaction
import org.queryquill.app.core.testing.repository.TestTransactionRepository
import org.queryquill.app.core.testing.util.MainDispatcherRule
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState

class TransactionViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: TestTransactionRepository
    private lateinit var viewModel: TransactionViewModel

    @Before
    fun setup() {
        repository = TestTransactionRepository()
        viewModel = TransactionViewModel(repository)
    }

    @Test
    fun `Initial state is Loading`() = runTest {
        assertEquals(TransactionsUiState.Loading, viewModel.transactions.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Emitting transactions updates UI state`() = runTest {
        val testTransactions = ImmutableList(
            listOf(
                Transaction(id = 1, label = "Test 1"), Transaction(id = 2, label = "Test 2")
            )
        )
        val model = GetTransactionModel(
            list = testTransactions, currentId = 1L
        )
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.transactions.collect()
        }
        repository.emitTransactions(model)
        assertEquals(
            TransactionsUiState.Success(testTransactions.list, 1L), viewModel.transactions.value
        )
    }

    @Test
    fun `DeleteTransaction event calls repository`() = runTest {
        viewModel.onEvent(TransactionEvent.DeleteTransaction(1L))
        assertEquals(1L, repository.lastDeletedTransactionId)
    }

    @Test
    fun `SetTransaction event calls repository`() = runTest {
        viewModel.onEvent(TransactionEvent.SetTransaction(2L))
        assertEquals(2L, repository.lastChangedTransactionId)
    }

    @Test
    fun `UpdateTransaction event calls repository`() = runTest {
        val transaction = Transaction(id = 3, label = "Updated transaction")
        viewModel.onEvent(TransactionEvent.UpdateTransaction(transaction))
        assertEquals(transaction, repository.lastUpdatedTransaction)
    }
}