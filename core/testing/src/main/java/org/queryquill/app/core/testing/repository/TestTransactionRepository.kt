/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.core.testing.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.queryquill.app.core.data.TransactionRepository
import org.queryquill.app.core.model.GetTransactionModel
import org.queryquill.app.core.model.NewTransactionModel
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.core.model.Transaction

class TestTransactionRepository : TransactionRepository {

    private val transactionFlow = MutableSharedFlow<GetTransactionModel>(replay = 1)
    private val requestFlow = MutableSharedFlow<RequestModel?>(replay = 1)
    private val responseFlow = MutableSharedFlow<ResponseModel?>(replay = 1)
    var lastDeletedTransactionId: Long? = null
    var lastUpdatedTransaction: Transaction? = null
    var lastChangedTransactionId: Long? = null


    override fun getTransactions(): Flow<GetTransactionModel> = transactionFlow.asSharedFlow()

    override fun getCurrentRequestOrNull(): Flow<RequestModel?> = requestFlow.asSharedFlow()

    override fun getCurrentResponseOrNull(): Flow<ResponseModel?> = responseFlow.asSharedFlow()

    override suspend fun changeCurrentTransaction(id: Long?) {
        lastChangedTransactionId = id
    }

    override suspend fun addTransaction(model: NewTransactionModel) {}

    override suspend fun updateRequest(model: RequestModel) {
        requestFlow.tryEmit(model)
    }

    override suspend fun deleteTransaction(id: Long) {
        lastDeletedTransactionId = id
    }

    override suspend fun updateTransaction(model: Transaction) {
        lastUpdatedTransaction = model
    }

    fun emitTransactions(model: GetTransactionModel) {
        transactionFlow.tryEmit(model)
    }
}