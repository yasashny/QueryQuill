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