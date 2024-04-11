package ru.yasdev.data.requestsDb

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.states.AuthState
import ru.yasdev.domain.requestsDb.states.BodyState
import ru.yasdev.domain.requestsDb.states.MultipartFormState

object Converters {
    private val jsonSerializer = Json {
        serializersModule = SerializersModule {
            polymorphic(
                MultipartFormState::class,
                MultipartFormState.Text::class,
                MultipartFormState.Text.serializer(),
            )
            polymorphic(
                MultipartFormState::class,
                MultipartFormState.BinaryFile::class,
                MultipartFormState.BinaryFile.serializer(),
            )
            polymorphic(
                AuthState::class, AuthState.NoAuth::class, AuthState.NoAuth.serializer()
            )
            polymorphic(
                AuthState::class, AuthState.Basic::class, AuthState.Basic.serializer()
            )
            polymorphic(
                BodyState::class, BodyState.Text::class, BodyState.Text.serializer()
            )
            polymorphic(
                BodyState::class, BodyState.MultipartForm::class, BodyState.MultipartForm.serializer()
            )
            polymorphic(
                BodyState::class, BodyState.FormUrlEncoded::class, BodyState.FormUrlEncoded.serializer()
            )
            polymorphic(
                BodyState::class, BodyState.BinaryFile::class, BodyState.BinaryFile.serializer()
            )
            polymorphic(
                BodyState::class, BodyState.NoBody::class, BodyState.NoBody.serializer()
            )
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromBody(bodyState: BodyState): String = jsonSerializer.encodeToString(bodyState)

    @TypeConverter
    @JvmStatic
    fun toBody(value: String): BodyState = jsonSerializer.decodeFromString<BodyState>(value)

    @TypeConverter
    @JvmStatic
    fun fromListItemList(list: List<KeyValue>): String = jsonSerializer.encodeToString(list)

    @TypeConverter
    @JvmStatic
    fun toListItemList(value: String): List<KeyValue> =
        jsonSerializer.decodeFromString<List<KeyValue>>(value)

    @TypeConverter
    fun httpTypeFromString(value: String): HttpType =
        jsonSerializer.decodeFromString<HttpType>(value)

    @TypeConverter
    fun httpTypeToString(httpType: HttpType): String = jsonSerializer.encodeToString(httpType)

    @TypeConverter
    @JvmStatic
    fun fromAuth(authState: AuthState): String = jsonSerializer.encodeToString(authState)

    @TypeConverter
    @JvmStatic
    fun toAuth(value: String): AuthState = jsonSerializer.decodeFromString<AuthState>(value)

}


