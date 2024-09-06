package org.queryquill.app.feature.request.auth

import org.queryquill.app.core.model.AuthState

internal fun AuthState.toEnum(): EnumAuthState {
    return when (this) {
        is AuthState.Basic -> EnumAuthState.Basic
        AuthState.NoAuth -> EnumAuthState.NoAuth
    }
}