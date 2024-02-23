package ru.yasdev.data.lastRequest

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

class LastRequestDataSource(val context: Context) {

    fun getId() = flow {
        context.dataStore.data.map { pref ->
            val preferences = pref[stringPreferencesKey("LastId")]
            if ((preferences == "null") or (preferences == null)) {
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
                    pref[stringPreferencesKey("LastId")] = "null"
                }
            }

            else -> {
                context.dataStore.edit { pref ->
                    pref[stringPreferencesKey("LastId")] = id.toString()
                }
            }
        }

    }

}

