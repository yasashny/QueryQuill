package com.yas.settings.mappers

import com.yas.settings.SettingsState
import com.yas.settings.models.SettingsDTO

internal fun SettingsDTO.toSettingsModel(): SettingsState.SettingsModel {
    return SettingsState.SettingsModel(theme = theme.toThemeState())
}

internal fun SettingsState.SettingsModel.toDTO(): SettingsDTO {
    return SettingsDTO(theme = theme.toDTO())
}