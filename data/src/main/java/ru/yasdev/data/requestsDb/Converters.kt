package ru.yasdev.data.requestsDb

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import ru.yasdev.domain.requestsDb.models.Auth
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.ListItem

object Converters {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun fromBody(body: Body): String {
        return gson.toJson(body)
    }
    @TypeConverter
    fun toBody(value: String): Body {
        val textType = object : TypeToken<Body.Text>() {}.type
        val formUrlEncodedType = object : TypeToken<Body.FormUrlEncoded>() {}.type

        val jsonObject = JsonParser.parseString(value).asJsonObject
        return when {
            jsonObject.has("text") -> gson.fromJson(value, textType)
            jsonObject.has("list") -> gson.fromJson(value, formUrlEncodedType)
            else -> Body.NoBody
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromListItemList(list: List<ListItem>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    @JvmStatic
    fun toListItemList(value: String): List<ListItem> {
        val listType = object : TypeToken<List<ListItem>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun httpTypeFromString(value: String): HttpType {
        return when (value) {
            "GET" -> HttpType.GET
            "POST" -> HttpType.POST
            "PUT" -> HttpType.PUT
            "PATCH" -> HttpType.PATCH
            "DELETE" -> HttpType.DELETE
            "OPTIONS" -> HttpType.OPTIONS
            "HEAD" -> HttpType.HEAD
            else -> {HttpType.OPTIONS}
        }
    }

    @TypeConverter
    fun httpTypeToString(httpType: HttpType): String {
        return httpType.name
    }
@TypeConverter
    @JvmStatic
    fun fromAuth(auth: Auth): String {
        val jsonObject = JsonObject()
        when (auth) {
            is Auth.NoAuth -> jsonObject.addProperty("type", "NoAuth")
            is Auth.Basic -> {
                jsonObject.addProperty("type", "Basic")
                jsonObject.addProperty("userName", auth.userName)
                jsonObject.addProperty("password", auth.password)
            }
        }
        return gson.toJson(jsonObject)
    }
@TypeConverter
    @JvmStatic
    fun toAuth(value: String): Auth {
        val jsonObject = JsonParser.parseString(value).asJsonObject
        return when (jsonObject.get("type").asString) {
            "NoAuth" -> Auth.NoAuth
            "Basic" -> {
                val userName = jsonObject.get("userName").asString
                val password = jsonObject.get("password").asString
                Auth.Basic(userName, password)
            }
            else -> throw IllegalArgumentException("Unknown Auth type")
        }
    }
}