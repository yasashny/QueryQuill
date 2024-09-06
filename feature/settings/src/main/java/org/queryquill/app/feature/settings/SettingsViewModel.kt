package org.queryquill.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.queryquill.app.data.settings.SettingsRepository

internal class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    val settingsUiState = repository.getSettings().map { newSettingsModel ->
        SettingsUiState.Success(newSettingsModel)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SettingsUiState.Loading)


    fun updateModel(updateSettings: UpdateSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = settingsUiState.value) {
                SettingsUiState.Loading -> {}
                is SettingsUiState.Success -> {
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
}