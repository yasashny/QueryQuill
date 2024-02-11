package ru.yasdev.domain.utils

sealed interface LastIdState {
    object Loading: LastIdState
    object Null: LastIdState
    data class Id(val id: Int): LastIdState
}