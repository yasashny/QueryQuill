package com.yas.requests.local.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrentRequestStorage(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

    companion object {
        private const val ID = "currentId"
        private const val NULL_STRING = "null"
    }

    fun getId(): Flow<Long?> {
        return context.dataStore.data.map { pref ->
            val preferences = pref[stringPreferencesKey(ID)]
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
                context.dataStore.edit { pref ->
                    pref[stringPreferencesKey(ID)] = NULL_STRING
                }
            }

            else -> {
                context.dataStore.edit { pref ->
                    pref[stringPreferencesKey(ID)] = id.toString()
                }
            }
        }
    }
}

