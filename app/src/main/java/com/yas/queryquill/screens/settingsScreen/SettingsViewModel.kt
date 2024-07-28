package com.yas.queryquill.screens.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.domain.settings.GetSettingsUseCase
import com.yas.domain.settings.SettingsModel
import com.yas.domain.settings.UpdateSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {

    private val _settingsModel = MutableStateFlow<SettingsModel?>(null)
    val settingsModel = _settingsModel.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSettingsUseCase.execute().collect { newSettingsModel ->
                _settingsModel.value = newSettingsModel
            }
        }
    }

    fun updateModel(updateSettings: UpdateSettings) {
        viewModelScope.launch(Dispatchers.IO) {
            when (updateSettings) {
                is UpdateSettings.UpdateTheme -> {
                    settingsModel.value?.copy(theme = updateSettings.theme)
                        ?.let { updateSettingsUseCase.execute(it) }
                }
            }
        }
    }
}