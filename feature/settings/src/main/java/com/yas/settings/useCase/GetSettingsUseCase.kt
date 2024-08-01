package com.yas.settings.useCase

import com.yas.settings.SettingsState
import com.yas.settings.mappers.toSettingsModel
import com.yas.settings_data.SettingsRepository
import com.yas.settings_data.models.SettingsDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class GetSettingsUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<SettingsState.SettingsModel> {
        return repository.getSettings().map { value: SettingsDTO -> value.toSettingsModel() }
    }
}