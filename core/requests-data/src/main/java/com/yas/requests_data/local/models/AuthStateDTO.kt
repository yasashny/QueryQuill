package com.yas.requests_data.local.models

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthStateDTO {

    @Serializable
    data object NoAuth : AuthStateDTO

    @Serializable
    data class Basic(val userName: String, val password: String) : AuthStateDTO
}