package com.yas.request.auth

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
import com.yas.model.AuthState
import com.yas.model.ImmutableList
import com.yas.request.R
import com.yas.request.alertDialog.ChangeTypeDialog
import com.yas.request.components.ChipGroup


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