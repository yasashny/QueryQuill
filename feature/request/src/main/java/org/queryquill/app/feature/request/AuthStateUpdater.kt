/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.feature.request

import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.RequestModel

internal object AuthStateUpdater {

    fun updateAuth(
        currentState: RequestModel, updateAuth: UpdateRequest.Auth
    ): RequestUiState? {
        return when (updateAuth) {
            is UpdateRequest.Auth.ChangeType -> changeAuthType(
                authType = updateAuth.authType,
                currentState = currentState,
                showDialog = updateAuth.onDirtyAuth,
                force = updateAuth.force
            )

            is UpdateRequest.Auth.Basic -> updateBasicAuth(
                basicState = updateAuth.basicState, currentState = currentState
            )
        }
    }

    private fun updateAuthType(
        authType: AuthState.Type, currentState: RequestModel
    ): RequestUiState {
        return when (authType) {
            AuthState.Type.NoAuth -> {
                RequestUiState.Success(currentState.copy(auth = AuthState.NoAuth))
            }

            AuthState.Type.Basic -> {
                RequestUiState.Success(currentState.copy(auth = AuthState.Basic()))
            }
        }
    }

    private fun isAuthDirty(currentState: AuthState): Boolean {
        return when (currentState) {
            is AuthState.NoAuth -> false
            is AuthState.Basic -> currentState.userName.isNotBlank() || currentState.password.isNotBlank()
        }
    }

    private fun changeAuthType(
        authType: AuthState.Type, currentState: RequestModel, showDialog: () -> Unit, force: Boolean
    ): RequestUiState? {
        if (currentState.auth.type == authType) {
            return null
        }
        if (!force) {
            val isDirty = isAuthDirty(
                currentState = currentState.auth
            )
            if (isDirty) {
                showDialog()
                return null
            } else {
                return updateAuthType(authType, currentState)
            }
        }
        return updateAuthType(authType, currentState)
    }

    private fun updateBasicAuth(
        basicState: AuthState.Basic, currentState: RequestModel
    ): RequestUiState {
        return RequestUiState.Success(currentState.copy(auth = basicState))
    }
}