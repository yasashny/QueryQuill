package ru.yasdev.data.LastRequest

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.yasdev.domain.utils.LastIdState


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

class LastRequestDataSource(val context: Context) {

    fun getId() = flow {



        context.dataStore.data.map { pref ->
            Log.d("QWERTY", "DDD")

            val preferences = pref[stringPreferencesKey("LastId")]
            Log.d("QWERTY", "qqq")
            if((preferences == "null") or (preferences == null)){
                Log.d("QWERTY", "www")
                emit(LastIdState.Null)
            }
            else{
                if (preferences != null) {
                    Log.d("QWERTY", "eee")
                    emit(LastIdState.Id(id = preferences.toInt()))
                }
            }
        }.collect()



    }



    suspend fun saveId(lastIdState: LastIdState){
        when(lastIdState){
            LastIdState.Loading ->{
                context.dataStore.edit {pref ->
                    pref[stringPreferencesKey("LastId")] = "null"
                }
            }
            LastIdState.Null -> {
                context.dataStore.edit {pref ->
                    pref[stringPreferencesKey("LastId")] = "null"
                }
            }
            is LastIdState.Id -> {
                context.dataStore.edit {pref ->
                    pref[stringPreferencesKey("LastId")] = lastIdState.id.toString()
                }
            }
        }

    }

}

