package org.queryquill.app.data.requests.models

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface AuthStateDTO {

    @Serializable
    data object NoAuth : AuthStateDTO

    @Serializable
    data class Basic(val userName: String, val password: String) : AuthStateDTO
}