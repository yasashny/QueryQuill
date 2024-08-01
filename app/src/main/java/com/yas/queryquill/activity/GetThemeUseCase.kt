package com.yas.queryquill.activity

import com.yas.common.ThemeState
import com.yas.settings_data.SettingsRepository
import com.yas.settings_data.models.SettingsDTO
import com.yas.settings_data.models.ThemeStateDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetThemeUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<ThemeState>{
        return repository.getSettings().map { value: SettingsDTO -> when(value.theme){
            ThemeStateDTO.SYSTEM -> ThemeState.SYSTEM
            ThemeStateDTO.DARK -> ThemeState.DARK
            ThemeStateDTO.LIGHT -> ThemeState.LIGHT
        } }
    }
}