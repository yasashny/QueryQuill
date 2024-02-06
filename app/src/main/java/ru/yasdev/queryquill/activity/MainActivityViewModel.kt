package ru.yasdev.queryquill.activity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.yasdev.queryquill.adaptive.TopAppBarState

class MainActivityViewModel: ViewModel() {

    private val _topAppBarState = MutableStateFlow<TopAppBarState>(TopAppBarState.SINGLE_SCREEN)
    val topAppBarState = _topAppBarState.asStateFlow()

    fun changeTopAppBarState(topAppBarState: TopAppBarState){
        _topAppBarState.value = topAppBarState
    }

}