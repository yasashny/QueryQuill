package com.yas.domain.settings

import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(private val repository: SettingsRepository) {

    fun execute(): Flow<SettingsModel> = repository.getSettings()
}