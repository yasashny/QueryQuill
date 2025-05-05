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

package org.queryquill.app.feature.request.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.alertDialog.ChangeTypeDialog
import org.queryquill.app.feature.request.components.ChipGroup


internal fun LazyListScope.authScreen(
    getAuthState: () -> AuthState,
    changeAuthType: (EnumAuthState) -> Unit,
    updateBasicAuth: (AuthState.Basic) -> Unit
) {
    item {
        Column {
            val authState = getAuthState()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 15.dp)
            ) {
                var openChangeTypeDialog by remember {
                    mutableStateOf(Pair(false, authState.toEnum()))
                }
                if (openChangeTypeDialog.first) {
                    ChangeTypeDialog(title = stringResource(R.string.auth), onDismiss = {
                        openChangeTypeDialog = Pair(false, openChangeTypeDialog.second)
                    }, onConfirm = {
                        changeAuthType(openChangeTypeDialog.second)
                        openChangeTypeDialog = Pair(false, openChangeTypeDialog.second)
                    })
                }
                ChipGroup(
                    currentState = authState.toEnum(), options = ImmutableList(
                        listOf(
                            EnumAuthState.NoAuth, EnumAuthState.Basic
                        )
                    )
                ) { newEnumState ->
                    if (newEnumState != authState.toEnum()) {
                        if (authState.isDefault()) {
                            changeAuthType(newEnumState)
                        } else {
                            openChangeTypeDialog = Pair(true, newEnumState)
                        }
                    }
                }
            }
            when (authState) {
                AuthState.NoAuth -> {}
                is AuthState.Basic -> {
                    AuthScreenBasic(authState = authState, updateAuth = updateBasicAuth)
                }
            }
        }
    }
}