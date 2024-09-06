package org.queryquill.app.data.settings

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.data.settings.mappers.toDTO
import org.queryquill.app.data.settings.mappers.toSettingsModel


class SettingsRepository internal constructor(
    private val storage: SettingsLocalDataSource, private val ioDispatcher: CoroutineDispatcher
) {

    fun getSettings(): Flow<SettingsModel> =
        storage.getSettings().flowOn(ioDispatcher).map { it.toSettingsModel() }

    suspend fun changeSettings(model: SettingsModel) {
        withContext(ioDispatcher) {
            storage.updateSettings(model.toDTO())
        }
    }
}