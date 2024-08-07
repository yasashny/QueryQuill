package com.yas.queryquill

import com.yas.model.ThemeState
import com.yas.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetThemeUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<ThemeState>{
        return repository.getSettings().map { it.themeState }
    }
}