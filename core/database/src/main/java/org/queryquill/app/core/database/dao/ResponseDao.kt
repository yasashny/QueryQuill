/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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