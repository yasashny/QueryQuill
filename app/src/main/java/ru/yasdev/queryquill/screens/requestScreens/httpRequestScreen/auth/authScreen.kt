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
                AuthChipGroup(authState = authState) { chipState ->
                    if(authState::class != chipState::class){
                        if(authState.isDefault()){
                            updateRequest(chipState)
                        }
                        else{
                            openDialog.value = Pair(true, chipState)
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