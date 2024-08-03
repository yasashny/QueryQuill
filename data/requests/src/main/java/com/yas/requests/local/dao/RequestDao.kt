package com.yas.requests.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yas.requests.local.models.RequestDBO
import com.yas.requests.local.models.RequestsListItemDTO
import kotlinx.coroutines.flow.Flow

@Dao
internal interface RequestDao {
    @Upsert
    suspend fun insertRequest(model: RequestDBO): Long

    @Query("DELETE FROM RequestDBO WHERE id = :id")
    suspend fun deleteRequest(id: Long)

    @Query("SELECT * FROM RequestDBO WHERE id = :id")
    suspend fun getRequest(id: Long): RequestDBO

    @Query("SELECT id, label FROM RequestDBO")
    fun getListOfRequests(): Flow<List<RequestsListItemDTO>>

}