package com.yas.request.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.AuthState
import com.yas.model.BasicState
import com.yas.request.R
import com.yas.request.alertDialog.ChangeTypeAlertDialog
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
                val openDialog = remember {
                    mutableStateOf(Pair(false, authState as BasicState))
                }
                if (openDialog.value.first) {
                    ChangeTypeAlertDialog(
                        openDialog, title = stringResource(R.string.auth)
                    ) { basicState ->
                        updateRequest(basicState as AuthState)
                    }
                }
                ChipGroup(
                    currentState = authState, options = listOf(
                        AuthState.NoAuth, AuthState.Basic.default()
                    )
                ) { newState ->
                    if (authState::class != newState::class) {
                        if (authState.isDefault()) {
                            updateRequest(newState as AuthState)
                        } else {
                            openDialog.value = Pair(true, newState as AuthState)
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