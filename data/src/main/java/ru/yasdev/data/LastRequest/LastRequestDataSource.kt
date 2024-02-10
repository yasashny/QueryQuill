package ru.yasdev.data.LastRequest

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

class LastRequestDataSource(val context: Context) {

    fun getId() =
        context.dataStore.data.map { pref ->
            return@map pref[intPreferencesKey("LastId")]
        }


    suspend fun saveId(id: Int){
        context.dataStore.edit {pref ->
            pref[intPreferencesKey("LastId")] = id
        }
    }

}

