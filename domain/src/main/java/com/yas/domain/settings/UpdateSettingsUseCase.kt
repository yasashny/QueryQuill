package com.yas.domain.settings

class UpdateSettingsUseCase(private val repository: SettingsRepository) {
    suspend fun execute(model: SettingsModel) = repository.changeSettings(model)
}