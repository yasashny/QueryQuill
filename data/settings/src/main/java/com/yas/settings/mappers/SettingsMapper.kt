package com.yas.settings.mappers

import com.yas.model.SettingsModel
import com.yas.settings.models.SettingsDTO

internal fun SettingsDTO.toSettingsModel(): SettingsModel {
    return SettingsModel(themeState = theme.toThemeState())
}

internal fun SettingsModel.toDTO(): SettingsDTO {
    return SettingsDTO(theme = themeState.toDTO())
}