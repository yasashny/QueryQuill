package com.yas.settings

import com.yas.model.SettingsModel
import com.yas.settings.mappers.toDTO
import com.yas.settings.mappers.toSettingsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


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