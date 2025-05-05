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