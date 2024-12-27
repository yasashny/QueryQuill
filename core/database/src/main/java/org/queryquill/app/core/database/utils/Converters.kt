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


