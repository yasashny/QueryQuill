package com.yas.settings_data

import com.yas.settings_data.models.SettingsDTO
import kotlinx.coroutines.flow.Flow


class SettingsRepository internal constructor(private val storage: SettingsStorage) {

    fun getSettings(): Flow<SettingsDTO> = storage.getSettings()

    suspend fun changeSettings(model: SettingsDTO) = storage.updateSettings(model)

}