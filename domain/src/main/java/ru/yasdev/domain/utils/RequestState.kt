package ru.yasdev.domain.utils
sealed interface RequestState {
    object Loading: RequestState
    object Null: RequestState
    object Request: RequestState
}