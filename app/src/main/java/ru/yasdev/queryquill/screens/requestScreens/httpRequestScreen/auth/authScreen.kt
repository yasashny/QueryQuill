package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.AuthState
import ru.yasdev.queryquill.components.AuthScreenAlertDialog
import ru.yasdev.queryquill.components.ChipGroup


fun LazyListScope.authScreen(
    authState: AuthState,
    updateRequest: (AuthState) -> Unit
) {
    item {
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 15.dp)
            ) {
                val openDialog = remember {
                    mutableStateOf(Pair(false, authState))
                }
                if (openDialog.value.first) {
                    AuthScreenAlertDialog(openDialog, updateRequest)
                }
                ChipGroup(currentState = authState, options = listOf(
                    AuthState.NoAuth, AuthState.Basic.default()
                )) {newState ->
                    if(authState::class != newState::class){
                        if(authState.isDefault()){
                            updateRequest(newState as AuthState)
                        }
                        else{
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