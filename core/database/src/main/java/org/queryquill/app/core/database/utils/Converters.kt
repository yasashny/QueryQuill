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

package org.queryquill.app.core.database.utils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.database.models.AuthStateDBO
import org.queryquill.app.core.database.models.BodyStateDBO
import org.queryquill.app.core.database.models.HttpTypeDBO
import org.queryquill.app.core.database.models.KeyValueDBO

internal class Converters {

    private val jsonSerializer = Json

    @TypeConverter
    fun fromBody(bodyState: BodyStateDBO): String = jsonSerializer.encodeToString(bodyState)

    @TypeConverter
    fun toBody(value: String): BodyStateDBO = jsonSerializer.decodeFromString<BodyStateDBO>(value)

    @TypeConverter
    fun fromListItemList(list: List<KeyValueDBO>): String = jsonSerializer.encodeToString(list)

    @TypeConverter
    fun toListItemList(value: String): List<KeyValueDBO> =
        jsonSerializer.decodeFromString<List<KeyValueDBO>>(value)

    @TypeConverter
    fun httpTypeFromString(value: String): HttpTypeDBO =
        jsonSerializer.decodeFromString<HttpTypeDBO>(value)

    @TypeConverter
    fun httpTypeToString(httpType: HttpTypeDBO): String = jsonSerializer.encodeToString(httpType)

    @TypeConverter
    fun fromAuth(authState: AuthStateDBO): String = jsonSerializer.encodeToString(authState)

    @TypeConverter
    fun toAuth(value: String): AuthStateDBO = jsonSerializer.decodeFromString<AuthStateDBO>(value)

    @TypeConverter
    fun fromContentType(contentType: ContentType): String =
        jsonSerializer.encodeToString(contentType)

    @TypeConverter
    fun toContentType(value: String): ContentType =
        jsonSerializer.decodeFromString<ContentType>(value)
}


