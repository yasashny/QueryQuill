package org.queryquill.app.core.database.models

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface AuthStateDBO {

    @Serializable
    data object NoAuth : AuthStateDBO

    @Serializable
    data class Basic(val userName: String, val password: String) : AuthStateDBO
}