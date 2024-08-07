package com.yas.response

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.ResponseModel
import com.yas.requests.local.TransactionsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class ResponseViewModel(
    repository: TransactionsRepository
) : ViewModel() {

    val responseUiState = repository.getCurrentResponseOrNull().map { responseOrNull ->
        if (responseOrNull != null) {
            ResponseUiState.Success(responseOrNull)
        } else {
            ResponseUiState.Success(ResponseModel.default())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ResponseUiState.Loading)

}