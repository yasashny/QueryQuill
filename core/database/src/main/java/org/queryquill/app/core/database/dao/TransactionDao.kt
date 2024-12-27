package org.queryquill.app.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.queryquill.app.core.database.models.TransactionEntity

@Dao
internal interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(model: TransactionEntity): Long

    @Query("DELETE FROM TransactionEntity WHERE id = :id")
    suspend fun deleteTransaction(id: Long)

    @Query("SELECT * FROM TransactionEntity")
    fun getTransactions(): Flow<List<TransactionEntity>>
}