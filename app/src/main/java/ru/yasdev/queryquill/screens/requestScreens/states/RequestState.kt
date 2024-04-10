package ru.yasdev.queryquill.screens.requestScreens.states

sealed interface RequestState {
    data object Loading : RequestState
    data object Null : RequestState
    data object Request : RequestState
}