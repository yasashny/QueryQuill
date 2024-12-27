package org.queryquill.app.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.queryquill.app.core.database.models.ResponseEntity

@Dao
internal interface ResponseDao {
    @Upsert
    suspend fun insertResponse(entity: ResponseEntity)

    @Query("DELETE FROM ResponseEntity WHERE id = :id")
    suspend fun deleteResponse(id: Long)

    @Query("SELECT * FROM ResponseEntity WHERE id = :id")
    fun getResponse(id: Long): Flow<ResponseEntity?>
}