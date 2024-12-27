package org.queryquill.app.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.core.model.ThemeState

class SettingsDataSource(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        private const val THEME = "theme"
        private const val DARK = "dark"
        private const val LIGHT = "light"
        private const val SYSTEM = "system"
    }


    fun getSettings(): Flow<SettingsModel> {
        return context.dataStore.data.map { pref ->
            val prefTheme = pref[stringPreferencesKey(THEME)]
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
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(THEME)] = when (model.themeState) {
                ThemeState.SYSTEM -> SYSTEM
                ThemeState.DARK -> DARK
                ThemeState.LIGHT -> LIGHT
            }
        }
    }
}