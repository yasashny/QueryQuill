package org.queryquill.app.data.requests.local.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.data.requests.models.AuthStateDTO
import org.queryquill.app.data.requests.models.BodyStateDTO
import org.queryquill.app.data.requests.models.HttpTypeDTO
import org.queryquill.app.data.requests.models.KeyValueDTO

internal class Converters {

    private val jsonSerializer = Json

    @TypeConverter
    fun fromBody(bodyState: BodyStateDTO): String = jsonSerializer.encodeToString(bodyState)

    @TypeConverter
    fun toBody(value: String): BodyStateDTO = jsonSerializer.decodeFromString<BodyStateDTO>(value)

    @TypeConverter
    fun fromListItemList(list: List<KeyValueDTO>): String = jsonSerializer.encodeToString(list)

    @TypeConverter
    fun toListItemList(value: String): List<KeyValueDTO> =
        jsonSerializer.decodeFromString<List<KeyValueDTO>>(value)

    @TypeConverter
    fun httpTypeFromString(value: String): HttpTypeDTO =
        jsonSerializer.decodeFromString<HttpTypeDTO>(value)

    @TypeConverter
    fun httpTypeToString(httpType: HttpTypeDTO): String = jsonSerializer.encodeToString(httpType)

    @TypeConverter
    fun fromAuth(authState: AuthStateDTO): String = jsonSerializer.encodeToString(authState)

    @TypeConverter
    fun toAuth(value: String): AuthStateDTO = jsonSerializer.decodeFromString<AuthStateDTO>(value)

    @TypeConverter
    fun fromContentType(contentType: ContentType): String =
        jsonSerializer.encodeToString(contentType)

    @TypeConverter
    fun toContentType(value: String): ContentType =
        jsonSerializer.decodeFromString<ContentType>(value)
}


