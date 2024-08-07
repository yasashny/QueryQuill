package com.yas.transaction.navigationDrawer

import com.yas.model.Transaction

sealed interface TransactionsUiState {
    data object Loading : TransactionsUiState
    data class Success(val list: List<Transaction>, val currentId : Long?) : TransactionsUiState
}