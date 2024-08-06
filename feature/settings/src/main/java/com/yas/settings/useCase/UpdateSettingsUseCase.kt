package com.yas.settings.useCase

import com.yas.model.SettingsModel
import com.yas.settings.SettingsRepository

internal class UpdateSettingsUseCase(private val repository: SettingsRepository) {

    suspend operator fun invoke(model: SettingsModel) {
        repository.changeSettings(model)
    }
}