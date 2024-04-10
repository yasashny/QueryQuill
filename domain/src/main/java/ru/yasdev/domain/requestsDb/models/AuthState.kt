package ru.yasdev.domain.requestsDb.models

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthState {
    @Serializable
    data object NoAuth : AuthState

    @Serializable
    data class Basic(val userName: String, val password: String) : AuthState

}