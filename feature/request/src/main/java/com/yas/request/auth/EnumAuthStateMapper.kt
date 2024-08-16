package com.yas.request.auth

import com.yas.model.AuthState

internal fun AuthState.toEnum(): EnumAuthState {
    return when (this) {
        is AuthState.Basic -> EnumAuthState.Basic
        AuthState.NoAuth -> EnumAuthState.NoAuth
    }
}

internal fun EnumAuthState.toAuthState(): AuthState {
    return when (this) {
        EnumAuthState.Basic -> AuthState.Basic.default()
        EnumAuthState.NoAuth -> AuthState.NoAuth
    }

}