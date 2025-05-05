/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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