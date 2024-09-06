package org.queryquill.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.queryquill.app.core.domain.GetThemeUseCase

class MainViewModel(getThemeUseCase: GetThemeUseCase) : ViewModel() {

    val themeState =
        getThemeUseCase.invoke().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

}