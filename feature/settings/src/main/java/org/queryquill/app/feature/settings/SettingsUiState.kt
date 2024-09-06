package org.queryquill.app.feature.settings

import org.queryquill.app.core.model.SettingsModel

internal sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settingsModel: SettingsModel) : SettingsUiState
}
