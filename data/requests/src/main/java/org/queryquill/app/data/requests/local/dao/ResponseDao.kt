package org.queryquill.app.data.requests.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.queryquill.app.data.requests.models.ResponseDBO

@Dao
internal interface ResponseDao {
    @Upsert
    suspend fun insertResponse(entity: ResponseDBO)

    @Query("DELETE FROM ResponseDBO WHERE id = :id")
    suspend fun deleteResponse(id: Long)

    @Query("SELECT * FROM ResponseDBO WHERE id = :id")
    fun getResponse(id: Long): Flow<ResponseDBO?>
}