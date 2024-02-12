package ru.yasdev.data.requestsDb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.yasdev.data.requestsDb.models.Request
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

@Dao
interface RequestDao {
    @Upsert
    suspend fun insertRequest(request: Request) : Long
    @Query("DELETE FROM Request WHERE id = :id")
    suspend fun deleteRequest(id: Int)
    @Query("SELECT * FROM Request WHERE id = :id")
    suspend fun getRequest(id: Int): RequestModel
    @Query("SELECT id, label FROM Request")
    fun getListOfRequests(): Flow<List<RequestsListItemModel>>

}