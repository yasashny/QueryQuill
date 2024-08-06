package com.yas.settings.useCase

import com.yas.model.SettingsModel
import com.yas.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

internal class GetSettingsUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<SettingsModel> {
        return repository.getSettings()
    }
}