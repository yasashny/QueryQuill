package com.yas.data.settings

import com.yas.domain.settings.SettingsModel
import com.yas.domain.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(private val storage: SettingsStorage) : SettingsRepository {
    override fun getSettings(): Flow<SettingsModel> = storage.getSettings()

    override suspend fun changeSettings(model: SettingsModel) = storage.updateSettings(model)

}