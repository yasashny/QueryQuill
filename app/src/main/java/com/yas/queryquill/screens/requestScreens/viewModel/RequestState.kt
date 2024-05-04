package com.yas.queryquill.screens.requestScreens.viewModel

sealed interface RequestState {
    data object Loading : RequestState
    data object Null : RequestState
    data object Request : RequestState
}