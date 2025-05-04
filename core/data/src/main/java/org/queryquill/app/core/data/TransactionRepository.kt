package org.queryquill.app.core.data

import kotlinx.coroutines.flow.Flow
import org.queryquill.app.core.model.GetTransactionModel
import org.queryquill.app.core.model.NewTransactionModel
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.core.model.Transaction

interface TransactionRepository {
    fun getTransactions(): Flow<GetTransactionModel>
    fun getCurrentRequestOrNull(): Flow<RequestModel?>
    fun getCurrentResponseOrNull(): Flow<ResponseModel?>
    suspend fun changeCurrentTransaction(id: Long?)
    suspend fun addTransaction(model: NewTransactionModel)
    suspend fun updateRequest(model: RequestModel)
    suspend fun deleteTransaction(id: Long)
    suspend fun updateTransaction(model: Transaction)
}