package com.yas.queryquill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.domain.GetThemeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(getThemeUseCase: GetThemeUseCase) : ViewModel() {

    val themeState =
        getThemeUseCase.invoke().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

}