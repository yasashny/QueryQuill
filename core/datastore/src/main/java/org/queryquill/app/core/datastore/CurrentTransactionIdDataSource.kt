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

class CurrentTransactionIdDataSource(private val preferences: DataStore<Preferences>) {
    companion object {
        private val ID_KEY = stringPreferencesKey("currentId")
        private const val NULL_STRING = "null"
    }

    fun getId(): Flow<Long?> {
        return preferences.data.map { pref ->
            val preferences = pref[ID_KEY]
            if ((preferences == NULL_STRING) || (preferences == null)) {
                null
            } else {
                preferences.toLong()
            }
        }
    }

    suspend fun saveId(id: Long?) {
        when (id) {
            null -> {
                preferences.edit { pref ->
                    pref[ID_KEY] = NULL_STRING
                }
            }

            else -> {
                preferences.edit { pref ->
                    pref[ID_KEY] = id.toString()
                }
            }
        }
    }
}

