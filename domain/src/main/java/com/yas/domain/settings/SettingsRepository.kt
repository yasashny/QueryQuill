package com.yas.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<SettingsModel>
    suspend fun changeSettings(model: SettingsModel)
}