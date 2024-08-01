package com.yas.queryquill.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.common.ThemeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getThemeUseCase: GetThemeUseCase
) : ViewModel() {
    private val _themeState = MutableStateFlow<ThemeState?>(null)
    val themeState = _themeState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getThemeUseCase.invoke().collect {
                _themeState.value = it
            }
        }
    }
}