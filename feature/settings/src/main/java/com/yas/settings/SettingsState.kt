package com.yas.settings

import com.yas.common.ThemeState

internal sealed interface SettingsState {
    data object Loading : SettingsState
    data class SettingsModel(val theme: ThemeState) : SettingsState
}
