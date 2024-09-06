package org.queryquill.app.feature.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.queryquill.app.data.requests.local.TransactionsRepository
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState

internal class TransactionViewModel(
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {

    val transactions = transactionsRepository.getTransactions().map { getTransactionModel ->
        TransactionsUiState.Success(getTransactionModel.list.list, getTransactionModel.currentId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TransactionsUiState.Loading)

    fun onEvent(transactionEvent: TransactionEvent) {
        when (transactionEvent) {

            is TransactionEvent.DeleteTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionsRepository.deleteTransaction(transactionEvent.id)
                }
            }

            is TransactionEvent.SetTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionsRepository.changeCurrentTransaction(transactionEvent.id)
                }
            }

            is TransactionEvent.UpdateTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionsRepository.updateTransaction(transactionEvent.transaction)
                }
            }
        }
    }
}