package org.queryquill.app.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.queryquill.app.core.database.models.RequestEntity

@Dao
internal interface RequestDao {
    @Upsert
    suspend fun insertRequest(model: RequestEntity): Long

    @Query("DELETE FROM RequestEntity WHERE id = :id")
    suspend fun deleteRequest(id: Long)

    @Query("SELECT * FROM RequestEntity WHERE id = :id")
    suspend fun getRequest(id: Long): RequestEntity?

}