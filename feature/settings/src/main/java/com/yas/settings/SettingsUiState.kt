package com.yas.settings

import com.yas.model.SettingsModel

internal sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settingsModel: SettingsModel) : SettingsUiState
}
