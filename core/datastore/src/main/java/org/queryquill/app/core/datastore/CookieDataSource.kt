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
}