package com.yas.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _settingsUiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val settingsState = _settingsUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSettings().collect { newSettingsModel ->
                _settingsUiState.value = SettingsUiState.Success(newSettingsModel)
            }
        }
    }

    fun updateModel(updateSettings: UpdateSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = settingsState.value) {
                SettingsUiState.Loading -> {}
                is SettingsUiState.Success -> {
                    when (updateSettings) {
                        is UpdateSettings.UpdateTheme -> {
                            state.settingsModel.copy(themeState = updateSettings.theme)
                                .let {repository.changeSettings(it) }
                        }
                    }
                }
            }
        }
    }
}