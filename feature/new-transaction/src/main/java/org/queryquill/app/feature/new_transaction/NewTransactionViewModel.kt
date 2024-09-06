package org.queryquill.app.feature.new_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.queryquill.app.core.model.NewTransactionModel
import org.queryquill.app.data.requests.local.TransactionsRepository

internal class NewTransactionViewModel(private val repository: TransactionsRepository) :
    ViewModel() {

    fun addNewTransaction(label: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(NewTransactionModel(label))
        }
    }
}