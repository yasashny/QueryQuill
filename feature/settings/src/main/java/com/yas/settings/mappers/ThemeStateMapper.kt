package com.yas.settings.mappers

import com.yas.common.ThemeState
import com.yas.settings_data.models.ThemeStateDTO

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