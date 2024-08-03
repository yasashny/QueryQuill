package com.yas.settings.mappers

import com.yas.settings.models.ThemeStateDTO

internal fun ThemeStateDTO.toThemeState(): com.yas.model.ThemeState {
    return when (this) {
        ThemeStateDTO.SYSTEM -> com.yas.model.ThemeState.SYSTEM
        ThemeStateDTO.DARK -> com.yas.model.ThemeState.DARK
        ThemeStateDTO.LIGHT -> com.yas.model.ThemeState.LIGHT
    }
}

internal fun com.yas.model.ThemeState.toDTO(): ThemeStateDTO {
    return when (this) {
        com.yas.model.ThemeState.SYSTEM -> ThemeStateDTO.SYSTEM
        com.yas.model.ThemeState.DARK -> ThemeStateDTO.DARK
        com.yas.model.ThemeState.LIGHT -> ThemeStateDTO.LIGHT
    }
}