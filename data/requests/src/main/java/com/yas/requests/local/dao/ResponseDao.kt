package com.yas.requests.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yas.requests.models.ResponseDBO
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ResponseDao {
    @Upsert
    suspend fun insertResponse(entity: ResponseDBO)

    @Query("DELETE FROM ResponseDBO WHERE id = :id")
    suspend fun deleteResponse(id: Long)

    @Query("SELECT * FROM ResponseDBO WHERE id = :id")
    fun getResponse(id: Long): Flow<ResponseDBO>
}