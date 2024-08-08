package com.yas.requests.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yas.requests.models.RequestDBO
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RequestDao {
    @Upsert
    suspend fun insertRequest(model: RequestDBO): Long

    @Query("DELETE FROM RequestDBO WHERE id = :id")
    suspend fun deleteRequest(id: Long)

    @Query("SELECT * FROM RequestDBO WHERE id = :id")
    fun getRequest(id: Long): Flow<RequestDBO?>

}