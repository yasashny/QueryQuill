package org.queryquill.app.data.requests.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.queryquill.app.data.requests.models.TransactionDBO

@Dao
internal interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(model: TransactionDBO): Long

    @Query("DELETE FROM TransactionDBO WHERE id = :id")
    suspend fun deleteTransaction(id: Long)

    @Query("SELECT * FROM TransactionDBO")
    fun getTransactions(): Flow<List<TransactionDBO>>
}