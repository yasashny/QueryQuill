package org.queryquill.app.feature.settings

import org.queryquill.app.core.model.ThemeState

internal sealed interface UpdateSettings {
    data class UpdateTheme(val theme: ThemeState) : UpdateSettings
}