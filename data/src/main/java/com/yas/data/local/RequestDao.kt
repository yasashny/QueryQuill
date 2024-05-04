package com.yas.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import com.yas.data.local.models.RequestEntity
import com.yas.domain.requestsDb.models.RequestsListItemModel

@Dao
interface RequestDao {
    @Upsert
    suspend fun insertRequest(model: RequestEntity): Long

    @Query("DELETE FROM RequestEntity WHERE id = :id")
    suspend fun deleteRequest(id: Int)

    @Query("SELECT * FROM RequestEntity WHERE id = :id")
    suspend fun getRequest(id: Int): RequestEntity

    @Query("SELECT id, label FROM RequestEntity")
    fun getListOfRequests(): Flow<List<RequestsListItemModel>>

}