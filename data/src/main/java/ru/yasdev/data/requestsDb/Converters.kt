package ru.yasdev.data.requestsDb

import android.net.Uri
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
        return when (body) {
            is Body.Text -> gson.toJson(body)
            is Body.FormUrlEncoded -> gson.toJson(body)
            is Body.BinaryFile -> gson.toJson(body.uri.toString())
            is Body.MultipartForm -> gson.toJson(body)
            is Body.NoBody -> gson.toJson(body)
        }
    }
    @TypeConverter
    @JvmStatic
    fun toBody(value: String): Body {
        try {
            val jsonObject = JsonParser.parseString(value).asJsonObject
            return when {
                jsonObject.has("text") -> gson.fromJson(value, object : TypeToken<Body.Text>() {}.type)
                jsonObject.has("list") -> gson.fromJson(value, object : TypeToken<Body.FormUrlEncoded>() {}.type)
                jsonObject.has("multipart") -> Body.MultipartForm
                jsonObject.has("noBody") -> Body.NoBody
                else -> throw IllegalArgumentException("Unknown JSON format for Body")
            }
        }catch (e: Exception){
            val resValue = value.replace("\"", "")
            return Body.BinaryFile(Uri.parse(resValue).normalizeScheme())
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