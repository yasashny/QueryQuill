package ru.yasdev.domain.requestsDb.models

sealed interface Auth {
    data object NoAuth: Auth
    data class Basic(val userName: String, val password: String): Auth


}