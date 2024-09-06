package org.queryquill.app.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.queryquill.app.data.settings.models.SettingsDTO
import org.queryquill.app.data.settings.models.ThemeStateDTO

internal class SettingsLocalDataSource(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        private const val THEME = "theme"
        private const val DARK = "dark"
        private const val LIGHT = "light"
        private const val SYSTEM = "system"
    }


    fun getSettings(): Flow<SettingsDTO> {
        return context.dataStore.data.map { pref ->
            val prefTheme = pref[stringPreferencesKey(THEME)]
            val theme = when (prefTheme) {
                null -> ThemeStateDTO.SYSTEM
                DARK -> ThemeStateDTO.DARK
                LIGHT -> ThemeStateDTO.LIGHT
                SYSTEM -> ThemeStateDTO.SYSTEM
                else -> ThemeStateDTO.SYSTEM
            }
            SettingsDTO(theme)
        }
    }


    suspend fun updateSettings(model: SettingsDTO) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(THEME)] = when (model.theme) {
                ThemeStateDTO.SYSTEM -> SYSTEM
                ThemeStateDTO.DARK -> DARK
                ThemeStateDTO.LIGHT -> LIGHT
            }
        }
    }
}