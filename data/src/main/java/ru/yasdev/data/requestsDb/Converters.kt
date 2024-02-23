package ru.yasdev.data.requestsDb

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
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
        val structuredType = object : TypeToken<Body.Structured>() {}.type

        val jsonObject = JsonParser.parseString(value).asJsonObject
        return if (jsonObject.has("text")) {
            gson.fromJson(value, textType)
        } else {
            gson.fromJson(value, structuredType)
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
}