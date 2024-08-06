package com.yas.settings

import com.yas.model.ThemeState

internal sealed interface UpdateSettings {
    data class UpdateTheme(val theme: ThemeState) : UpdateSettings
}