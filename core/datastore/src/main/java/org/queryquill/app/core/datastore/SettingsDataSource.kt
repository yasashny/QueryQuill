package org.queryquill.app.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.core.model.ThemeState

class SettingsDataSource(private val preferences: DataStore<Preferences>) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
        private const val DARK = "dark"
        private const val LIGHT = "light"
        private const val SYSTEM = "system"
    }

    fun getSettings(): Flow<SettingsModel> {
        return preferences.data.map { pref ->
            val prefTheme = pref[THEME_KEY]
            val theme = when (prefTheme) {
                null -> ThemeState.SYSTEM
                DARK -> ThemeState.DARK
                LIGHT -> ThemeState.LIGHT
                SYSTEM -> ThemeState.SYSTEM
                else -> ThemeState.SYSTEM
            }
            SettingsModel(theme)
        }
    }

    suspend fun updateSettings(model: SettingsModel) {
        preferences.edit { pref ->
            pref[THEME_KEY] = when (model.themeState) {
                ThemeState.SYSTEM -> SYSTEM
                ThemeState.DARK -> DARK
                ThemeState.LIGHT -> LIGHT
            }
        }
    }
}