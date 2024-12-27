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