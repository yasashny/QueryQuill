package org.queryquill.app.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.queryquill.app.core.datastore.SettingsDataSource
import org.queryquill.app.core.model.SettingsModel


class SettingsRepository internal constructor(
    private val storage: SettingsDataSource, private val ioDispatcher: CoroutineDispatcher
) {

    fun getSettings(): Flow<SettingsModel> =
        storage.getSettings().flowOn(ioDispatcher)

    suspend fun changeSettings(model: SettingsModel) {
        withContext(ioDispatcher) {
            storage.updateSettings(model)
        }
    }
}