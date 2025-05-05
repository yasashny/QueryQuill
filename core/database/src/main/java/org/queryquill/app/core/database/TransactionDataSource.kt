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

package org.queryquill.app.core.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.queryquill.app.core.database.dao.TransactionDao
import org.queryquill.app.core.database.mappers.asEntity
import org.queryquill.app.core.database.mappers.asExternalModel
import org.queryquill.app.core.database.models.TransactionEntity
import org.queryquill.app.core.model.NewTransactionModel
import org.queryquill.app.core.model.Transaction

class TransactionDataSource internal constructor(private val dao: TransactionDao) {

    suspend fun create(newTransactionModel: NewTransactionModel): Long {
        return dao.insertTransaction(
            TransactionEntity(
                label = newTransactionModel.label
            )
        )
    }

    fun getTransactions(): Flow<List<Transaction>> {
        return dao.getTransactions().map { list -> list.map { it.asExternalModel()} }
    }

    suspend fun update(model: Transaction) {
        dao.insertTransaction(model.asEntity())
    }

    suspend fun delete(id: Long) {
        dao.deleteTransaction(id)
    }
}