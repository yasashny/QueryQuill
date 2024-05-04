package com.yas.data.lastRequest

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LastRequestDataSource(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

    private val id = "LastId"
    private val nullString = "null"

    fun getId() = flow {
        context.dataStore.data.map { pref ->
            val preferences = pref[stringPreferencesKey(id)]
            if ((preferences == nullString) or (preferences == null)) {
                emit(null)
            } else {
                if (preferences != null) {
                    emit(preferences.toInt())
                }
            }
        }.collect()
    }

    suspend fun saveId(id: Int?) {

        when (id) {
            null -> {
                context.dataStore.edit { pref ->
                    pref[stringPreferencesKey(this.id)] = nullString
                }
            }
            else -> {
                context.dataStore.edit { pref ->
                    pref[stringPreferencesKey(this.id)] = id.toString()
                }
            }
        }
    }
}

