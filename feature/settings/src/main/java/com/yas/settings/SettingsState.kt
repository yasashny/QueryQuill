package com.yas.settings

import com.yas.model.ThemeState

internal sealed interface SettingsState {
    data object Loading : SettingsState
    data class SettingsModel(val theme: com.yas.model.ThemeState) : SettingsState
}
