package org.queryquill.app.data.settings.mappers

import org.queryquill.app.core.model.ThemeState
import org.queryquill.app.data.settings.models.ThemeStateDTO

internal fun ThemeStateDTO.toThemeState(): ThemeState {
    return when (this) {
        ThemeStateDTO.SYSTEM -> ThemeState.SYSTEM
        ThemeStateDTO.DARK -> ThemeState.DARK
        ThemeStateDTO.LIGHT -> ThemeState.LIGHT
    }
}

internal fun ThemeState.toDTO(): ThemeStateDTO {
    return when (this) {
        ThemeState.SYSTEM -> ThemeStateDTO.SYSTEM
        ThemeState.DARK -> ThemeStateDTO.DARK
        ThemeState.LIGHT -> ThemeStateDTO.LIGHT
    }
}