package ru.yasdev.data.requestsDb

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import ru.yasdev.domain.requestsDb.models.Auth
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.domain.requestsDb.models.MultipartFormState

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
                MultipartFormState.File::class,
                MultipartFormState.File.serializer(),
            )
            polymorphic(
                Auth::class, Auth.NoAuth::class, Auth.NoAuth.serializer()
            )
            polymorphic(
                Auth::class, Auth.Basic::class, Auth.Basic.serializer()
            )
            polymorphic(
                Body::class, Body.Text::class, Body.Text.serializer()
            )
            polymorphic(
                Body::class, Body.MultipartForm::class, Body.MultipartForm.serializer()
            )
            polymorphic(
                Body::class, Body.FormUrlEncoded::class, Body.FormUrlEncoded.serializer()
            )
            polymorphic(
                Body::class, Body.BinaryFile::class, Body.BinaryFile.serializer()
            )
            polymorphic(
                Body::class, Body.NoBody::class, Body.NoBody.serializer()
            )
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromBody(body: Body): String = jsonSerializer.encodeToString(body)

    @TypeConverter
    @JvmStatic
    fun toBody(value: String): Body = jsonSerializer.decodeFromString<Body>(value)

    @TypeConverter
    @JvmStatic
    fun fromListItemList(list: List<ListItem>): String = jsonSerializer.encodeToString(list)

    @TypeConverter
    @JvmStatic
    fun toListItemList(value: String): List<ListItem> = jsonSerializer.decodeFromString<List<ListItem>>(value)

    @TypeConverter
    fun httpTypeFromString(value: String): HttpType = jsonSerializer.decodeFromString<HttpType>(value)

    @TypeConverter
    fun httpTypeToString(httpType: HttpType): String = jsonSerializer.encodeToString(httpType)

    @TypeConverter
    @JvmStatic
    fun fromAuth(auth: Auth): String = jsonSerializer.encodeToString(auth)

    @TypeConverter
    @JvmStatic
    fun toAuth(value: String): Auth = jsonSerializer.decodeFromString<Auth>(value)

}


