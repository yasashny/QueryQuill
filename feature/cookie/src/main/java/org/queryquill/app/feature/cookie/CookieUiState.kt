package org.queryquill.app.feature.cookie

import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.SettingsModel

internal sealed interface CookieUiState {
    data object Loading : CookieUiState
    data class Success(val list: List<KeyValue>) : CookieUiState
}