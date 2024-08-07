package com.yas.requests.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yas.requests.models.TransactionDBO
import kotlinx.coroutines.flow.Flow

@Dao
internal interface TransactionDao {

    @Upsert
    suspend fun insertTransaction(model: TransactionDBO): Long

    @Query("DELETE FROM TransactionDBO WHERE id = :id")
    suspend fun deleteTransaction(id: Long)

    @Query("SELECT * FROM TransactionDBO")
    fun getTransactions(): Flow<List<TransactionDBO>>
}