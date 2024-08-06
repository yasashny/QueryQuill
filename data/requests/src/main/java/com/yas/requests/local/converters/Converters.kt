package com.yas.requests.local.converters

import androidx.room.TypeConverter
import com.yas.requests.models.AuthStateDTO
import com.yas.requests.models.BodyStateDTO
import com.yas.requests.models.HttpTypeDTO
import com.yas.requests.models.KeyValueDTO
import com.yas.requests.models.MultipartFormStateDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

internal object Converters {
    private val jsonSerializer = Json {
        serializersModule = SerializersModule {
            polymorphic(
                MultipartFormStateDTO::class,
                MultipartFormStateDTO.Text::class,
                MultipartFormStateDTO.Text.serializer(),
            )
            polymorphic(
                MultipartFormStateDTO::class,
                MultipartFormStateDTO.BinaryFile::class,
                MultipartFormStateDTO.BinaryFile.serializer(),
            )
            polymorphic(
                AuthStateDTO::class, AuthStateDTO.NoAuth::class, AuthStateDTO.NoAuth.serializer()
            )
            polymorphic(
                AuthStateDTO::class, AuthStateDTO.Basic::class, AuthStateDTO.Basic.serializer()
            )
            polymorphic(
                BodyStateDTO::class, BodyStateDTO.Text::class, BodyStateDTO.Text.serializer()
            )
            polymorphic(
                BodyStateDTO::class,
                BodyStateDTO.MultipartForm::class,
                BodyStateDTO.MultipartForm.serializer()
            )
            polymorphic(
                BodyStateDTO::class,
                BodyStateDTO.FormUrlEncoded::class,
                BodyStateDTO.FormUrlEncoded.serializer()
            )
            polymorphic(
                BodyStateDTO::class,
                BodyStateDTO.BinaryFile::class,
                BodyStateDTO.BinaryFile.serializer()
            )
            polymorphic(
                BodyStateDTO::class, BodyStateDTO.NoBody::class, BodyStateDTO.NoBody.serializer()
            )
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromBody(bodyState: BodyStateDTO): String = jsonSerializer.encodeToString(bodyState)

    @TypeConverter
    @JvmStatic
    fun toBody(value: String): BodyStateDTO = jsonSerializer.decodeFromString<BodyStateDTO>(value)

    @TypeConverter
    @JvmStatic
    fun fromListItemList(list: List<KeyValueDTO>): String = jsonSerializer.encodeToString(list)

    @TypeConverter
    @JvmStatic
    fun toListItemList(value: String): List<KeyValueDTO> =
        jsonSerializer.decodeFromString<List<KeyValueDTO>>(value)

    @TypeConverter
    @JvmStatic
    fun httpTypeFromString(value: String): HttpTypeDTO =
        jsonSerializer.decodeFromString<HttpTypeDTO>(value)

    @TypeConverter
    @JvmStatic
    fun httpTypeToString(httpType: HttpTypeDTO): String = jsonSerializer.encodeToString(httpType)

    @TypeConverter
    @JvmStatic
    fun fromAuth(authState: AuthStateDTO): String = jsonSerializer.encodeToString(authState)

    @TypeConverter
    @JvmStatic
    fun toAuth(value: String): AuthStateDTO = jsonSerializer.decodeFromString<AuthStateDTO>(value)

}


