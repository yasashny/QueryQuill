package com.yas.settings.useCase

import com.yas.settings.SettingsState
import com.yas.settings.mappers.toDTO
import com.yas.settings.SettingsRepository

internal class UpdateSettingsUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(model: SettingsState.SettingsModel) {
        repository.changeSettings(model.toDTO())
    }
}