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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.queryquill.app.core.data.TransactionRepository
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState

internal class TransactionViewModel(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val transactions = transactionRepository.getTransactions().map { getTransactionModel ->
        TransactionsUiState.Success(getTransactionModel.list.list, getTransactionModel.currentId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TransactionsUiState.Loading)

    fun onEvent(transactionEvent: TransactionEvent) {
        when (transactionEvent) {

            is TransactionEvent.DeleteTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionRepository.deleteTransaction(transactionEvent.id)
                }
            }

            is TransactionEvent.SetTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionRepository.changeCurrentTransaction(transactionEvent.id)
                }
            }

            is TransactionEvent.UpdateTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionRepository.updateTransaction(transactionEvent.transaction)
                }
            }
        }
    }
}