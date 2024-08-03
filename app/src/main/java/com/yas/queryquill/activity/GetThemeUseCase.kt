package com.yas.queryquill.activity

import com.yas.settings.SettingsRepository
import com.yas.settings.models.SettingsDTO
import com.yas.settings.models.ThemeStateDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetThemeUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<com.yas.model.ThemeState>{
        return repository.getSettings().map { value: SettingsDTO -> when(value.theme){
            ThemeStateDTO.SYSTEM -> com.yas.model.ThemeState.SYSTEM
            ThemeStateDTO.DARK -> com.yas.model.ThemeState.DARK
            ThemeStateDTO.LIGHT -> com.yas.model.ThemeState.LIGHT
        } }
    }
}