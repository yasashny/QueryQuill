package org.queryquill.app.feature.new_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.queryquill.app.core.data.TransactionsRepository
import org.queryquill.app.core.model.NewTransactionModel

internal class NewTransactionViewModel(private val repository: TransactionsRepository) :
    ViewModel() {

    fun addNewTransaction(label: String) {
        viewModelScope.launch {
            repository.addTransaction(NewTransactionModel(label))
        }
    }
}