package ru.yasdev.data.requestsDb

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.yasdev.data.requestsDb.models.DataRequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

@Dao
interface RequestDao {
    @Upsert
    suspend fun insertRequest(model: DataRequestModel): Long

    @Query("DELETE FROM DataRequestModel WHERE id = :id")
    suspend fun deleteRequest(id: Int)

    @Query("SELECT * FROM DataRequestModel WHERE id = :id")
    suspend fun getRequest(id: Int): DataRequestModel

    @Query("SELECT id, label FROM DataRequestModel")
    fun getListOfRequests(): Flow<List<RequestsListItemModel>>

}