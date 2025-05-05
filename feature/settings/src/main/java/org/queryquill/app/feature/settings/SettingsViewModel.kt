/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.queryquill.app.core.data.SettingsRepository

internal class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val settingsUiState = repository.getSettings().map { newSettingsModel ->
        SettingsUiState.Success(newSettingsModel)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SettingsUiState.Loading)


    fun updateModel(updateSettings: UpdateSettings) {
        viewModelScope.launch {
            val state = settingsUiState.value
            if (state is SettingsUiState.Success) {
                when (updateSettings) {
                    is UpdateSettings.UpdateTheme -> {
                        state.settingsModel.copy(themeState = updateSettings.theme)
                            .let { repository.changeSettings(it) }
                    }
                }
            }
        }
    }
}