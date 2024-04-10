package ru.yasdev.domain.requestsDb.models

import kotlinx.serialization.Serializable

@Serializable
sealed interface Auth {
    @Serializable
    data object NoAuth: Auth
    @Serializable
    data class Basic(val userName: String, val password: String): Auth


}