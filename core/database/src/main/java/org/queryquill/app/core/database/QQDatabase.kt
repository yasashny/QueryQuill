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

package org.queryquill.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.queryquill.app.core.database.utils.Converters
import org.queryquill.app.core.database.dao.RequestDao
import org.queryquill.app.core.database.dao.ResponseDao
import org.queryquill.app.core.database.dao.TransactionDao
import org.queryquill.app.core.database.models.RequestEntity
import org.queryquill.app.core.database.models.ResponseEntity
import org.queryquill.app.core.database.models.TransactionEntity

@Database(
    entities = [RequestEntity::class, ResponseEntity::class, TransactionEntity::class], version = 1
)
@TypeConverters(Converters::class)
internal abstract class QQDatabase : RoomDatabase() {

    abstract val requestDao: RequestDao
    abstract val responseDao: ResponseDao
    abstract val transactionDao: TransactionDao
}