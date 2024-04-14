package ru.yasdev.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ru.yasdev.data.local.models.ResponseEntity

@Dao
interface ResponseDao {
    @Upsert
    suspend fun insertResponse(entity: ResponseEntity)

    @Query("DELETE FROM ResponseEntity WHERE id = :id")
    suspend fun deleteResponse(id: Int)

    @Query("SELECT * FROM ResponseEntity WHERE id = :id")
    suspend fun getResponse(id: Int): ResponseEntity
}