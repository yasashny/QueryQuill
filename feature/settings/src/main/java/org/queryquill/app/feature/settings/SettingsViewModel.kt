package org.queryquill.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SettingsUiState.Loading)


    fun updateModel(updateSettings: UpdateSettings) {
        viewModelScope.launch{
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