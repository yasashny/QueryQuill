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