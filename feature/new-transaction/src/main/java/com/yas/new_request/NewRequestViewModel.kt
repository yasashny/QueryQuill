package com.yas.new_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.NewTransactionModel
import com.yas.requests.local.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class NewRequestViewModel(private val repository: TransactionsRepository) : ViewModel() {

    private val _newTransactionModel = MutableStateFlow(NewTransactionModel(""))
    val newRequestModel = _newTransactionModel.asStateFlow()

    fun addNewRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransaction(newRequestModel.value)
        }
    }

    fun onEvent(event: UpdateNewRequestModel) {
        when (event) {
            is UpdateNewRequestModel.UpdateLabel -> {
                _newTransactionModel.value = newRequestModel.value.copy(label = event.label)
            }
        }
    }
}