package com.yas.new_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.NewTransactionModel
import com.yas.requests.local.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class NewTransactionViewModel(private val repository: TransactionsRepository) :
    ViewModel() {

    fun addNewTransaction(label: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(NewTransactionModel(label))
        }
    }
}