package com.yas.transaction.navigationDrawer

import com.yas.model.Transaction

internal sealed interface TransactionsUiState {
    data object Loading : TransactionsUiState
    data class Success(val list: List<Transaction>, val currentId: Long?) : TransactionsUiState
}