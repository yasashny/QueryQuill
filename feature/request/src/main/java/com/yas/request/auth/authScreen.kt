package com.yas.request.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import com.yas.model.AuthState
import com.yas.model.BasicState
import com.yas.model.ImmutableList
import com.yas.request.R
import com.yas.request.alertDialog.ChangeTypeDialog
import com.yas.request.components.ChipGroup


internal fun LazyListScope.authScreen(
    authState: AuthState, updateRequest: (AuthState) -> Unit
) {
    item {
        Row {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 15.dp)
            ) {
                var openChangeTypeDialog by remember {
                    mutableStateOf(Pair(false, authState as BasicState))
                }
                if (openChangeTypeDialog.first) {
                    ChangeTypeDialog(title = stringResource(R.string.auth), onDismiss = {
                        openChangeTypeDialog = Pair(false, openChangeTypeDialog.second)
                    }, onConfirm = {
                        updateRequest(openChangeTypeDialog.second as AuthState)
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
                    val newState = newEnumState.toAuthState()
                    if (authState::class != newState::class) {
                        if (authState.isDefault()) {
                            updateRequest(newState)
                        } else {
                            openChangeTypeDialog = Pair(true, newState)
                        }
                    }
                }
            }
        }
    }

    when (authState) {
        AuthState.NoAuth -> {}
        is AuthState.Basic -> {
            item {
                AuthScreenBasic(authState = authState, updateRequest)
            }
        }
    }
}