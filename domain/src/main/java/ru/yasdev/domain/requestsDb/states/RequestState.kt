package ru.yasdev.domain.requestsDb.states

sealed interface RequestState {
    data object Loading : RequestState
    data object Null : RequestState
    data object Request : RequestState
}