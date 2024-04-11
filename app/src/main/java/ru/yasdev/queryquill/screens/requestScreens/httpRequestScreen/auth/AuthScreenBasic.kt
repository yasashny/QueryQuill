package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.AuthState

@Composable
fun AuthScreenBasic(authState: AuthState.Basic, updateRequest: (AuthState.Basic) -> Unit) {
    Column {
        OutlinedTextField(
            value = authState.userName,
            onValueChange = { userName ->
                updateRequest(
                    AuthState.Basic(
                        userName, authState.password
                    )
                )
            },
            label = { Text(text = "User name") },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = authState.password,
            onValueChange = { password ->
                updateRequest(
                    AuthState.Basic(
                        authState.userName, password
                    )
                )
            },
            label = { Text(text = "Password") },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .fillMaxWidth()
        )
    }
}