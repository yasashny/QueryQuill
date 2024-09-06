package org.queryquill.app.feature.transaction.navigationDrawer

import org.queryquill.app.core.model.Transaction

internal sealed interface TransactionsUiState {
    data object Loading : TransactionsUiState
    data class Success(val list: List<Transaction>, val currentId: Long?) : TransactionsUiState
}