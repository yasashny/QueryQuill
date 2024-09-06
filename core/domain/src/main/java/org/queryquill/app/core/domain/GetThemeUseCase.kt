package org.queryquill.app.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.queryquill.app.core.model.ThemeState
import org.queryquill.app.data.settings.SettingsRepository

class GetThemeUseCase(private val repository: SettingsRepository) {

    operator fun invoke(): Flow<ThemeState> {
        return repository.getSettings().map { it.themeState }
    }
}