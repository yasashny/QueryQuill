package org.queryquill.app.data.requests.local.dataSource

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import org.queryquill.app.data.requests.local.db.RequestsDataBase
import org.queryquill.app.data.requests.models.NewTransactionModelDTO
import org.queryquill.app.data.requests.models.TransactionDBO

internal class TransactionLocalDataSource(context: Context) {

    private val db = Room.databaseBuilder(
        context, RequestsDataBase::class.java, "request.db"
    ).build()


    suspend fun create(newTransactionModelDTO: NewTransactionModelDTO): Long {
        return db.transactionDao.insertTransaction(
            TransactionDBO(
                label = newTransactionModelDTO.label
            )
        )
    }

    fun getTransactions(): Flow<List<TransactionDBO>> {
        return db.transactionDao.getTransactions()
    }

    suspend fun update(model: TransactionDBO) {
        db.transactionDao.insertTransaction(model)
    }

    suspend fun delete(id: Long) {
        db.transactionDao.deleteTransaction(id)
    }


}