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

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.UpdateRequest
import org.queryquill.app.feature.request.components.ChipGroup
import org.queryquill.app.feature.request.dialog.ChangeTypeDialog

internal fun LazyListScope.authScreen(
    state: AuthState, onAuthEvent: (UpdateRequest.Auth) -> Unit
) {
    item("auth_chip_group") {
        var pendingType by rememberSaveable { mutableStateOf<AuthState.Type?>(null) }
        pendingType?.let { targetType ->
            ChangeTypeDialog(title = stringResource(R.string.auth), onDismiss = {
                pendingType = null
            }, onConfirm = {
                onAuthEvent(
                    UpdateRequest.Auth.ChangeType(
                        targetType, force = true
                    )
                )
                pendingType = null
            })
        }
        ChipGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .animateItem(fadeOutSpec = null),
            current = state.type,
            options = AuthState.Type.entries,
            onSelect = { newState ->
                newState.takeIf { it != state.type }?.let { selected ->
                    onAuthEvent(
                        UpdateRequest.Auth.ChangeType(
                            selected
                        ) {
                            pendingType = selected
                        })
                }
            })
    }

    when (state) {
        AuthState.NoAuth -> Unit
        is AuthState.Basic -> item("auth_basic_screen") {
            AuthScreenBasic(
                modifier = Modifier.animateItem(fadeOutSpec = null),
                authState = state,
                onAuthChange = { newState -> onAuthEvent(UpdateRequest.Auth.Basic(newState)) })
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewAuthScreen() {
    QueryQuillTheme {
        LazyColumn {
            authScreen(state = AuthState.Basic("", "")) {}
        }
    }
}
