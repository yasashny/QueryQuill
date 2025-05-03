package org.queryquill.app.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class CookieDataSource(
    private val preferences: DataStore<Preferences>, private val json: Json = Json
) {

    companion object {
        private val COOKIE_KEY = stringPreferencesKey("cookie")
    }

    fun getCookie(): Flow<List<String>> {
        return preferences.data.map { preferences ->
            json.decodeFromString<List<String>>(preferences[COOKIE_KEY] ?: "[]")
        }
    }

    suspend fun updateCookie(list: List<String>) {
        preferences.edit { preferences ->
            preferences[COOKIE_KEY] = json.encodeToString(list)
        }
    }

    suspend fun addNewCookie(list: List<String>) {
        preferences.edit { preferences ->
            val currentCookies = json.decodeFromString<List<String>>(preferences[COOKIE_KEY] ?: "[]")
                val cookieMap = mutableMapOf<Triple<String?, String?, String?>, String>()
            
            currentCookies.forEach { cookie ->
                parseCookie(cookie)?.let { params ->
                    val key = Triple(params.name, params.domain, params.path)
                    cookieMap[key] = cookie
                }
            }

            list.forEach { newCookie ->
                parseCookie(newCookie)?.let { newParams ->
                    val key = Triple(newParams.name, newParams.domain, newParams.path)
                    cookieMap[key] = newCookie
                }
            }

            preferences[COOKIE_KEY] = json.encodeToString(cookieMap.values.toList())
        }
    }

    private fun parseCookie(cookie: String): CookieParams? {
        val parts = cookie.split(';').map { it.trim() }
        if (parts.isEmpty()) return null
        val nameValue = parts[0].split('=', limit = 2)
        if (nameValue.size != 2) return null
        val (name, value) = nameValue
        var domain: String? = null
        var path: String? = null
        var isSecure = false
        parts.drop(1).forEach { part ->
            when {
                part.equals("Secure", ignoreCase = true) -> isSecure = true
                part.startsWith("Domain=", ignoreCase = true) ->
                    domain = part.substringAfter('=').trim().removePrefix(".")
                part.startsWith("Path=", ignoreCase = true) ->
                    path = part.substringAfter('=').trim()
            }
        }

        return CookieParams(
            name = name,
            value = value,
            domain = domain,
            path = path ?: "/",
            isSecure = isSecure
        )
    }

    private data class CookieParams(
        val name: String,
        val value: String,
        val domain: String?,
        val path: String,
        val isSecure: Boolean
    )
}