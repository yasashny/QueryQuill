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

