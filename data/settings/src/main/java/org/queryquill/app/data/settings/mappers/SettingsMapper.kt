package org.queryquill.app.data.settings.mappers

import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.data.settings.models.SettingsDTO

internal fun SettingsDTO.toSettingsModel(): SettingsModel {
    return SettingsModel(themeState = theme.toThemeState())
}

internal fun SettingsModel.toDTO(): SettingsDTO {
    return SettingsDTO(theme = themeState.toDTO())
}