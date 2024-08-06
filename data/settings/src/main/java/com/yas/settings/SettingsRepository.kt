package com.yas.settings

import com.yas.model.SettingsModel
import com.yas.settings.mappers.toDTO
import com.yas.settings.mappers.toSettingsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepository internal constructor(private val storage: SettingsLocalDataSource) {

    fun getSettings(): Flow<SettingsModel> = storage.getSettings().map { it.toSettingsModel() }

    suspend fun changeSettings(model: SettingsModel) = storage.updateSettings(model.toDTO())

}