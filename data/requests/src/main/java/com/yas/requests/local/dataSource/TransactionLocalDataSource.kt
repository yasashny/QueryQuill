package com.yas.requests.local.dataSource

import android.content.Context
import androidx.room.Room
import com.yas.requests.local.db.RequestsDataBase
import com.yas.requests.models.NewTransactionModelDTO
import com.yas.requests.models.TransactionDBO
import kotlinx.coroutines.flow.Flow

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