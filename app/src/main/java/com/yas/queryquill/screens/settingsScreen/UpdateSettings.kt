package com.yas.queryquill.screens.settingsScreen

import com.yas.domain.settings.ThemeState

sealed interface UpdateSettings {
    data class UpdateTheme(val theme: ThemeState) : UpdateSettings
}