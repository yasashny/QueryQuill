package org.queryquill.app.feature.request.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.feature.request.R

@Composable
internal fun AuthScreenBasic(authState: AuthState.Basic, updateAuth: (AuthState.Basic) -> Unit) {
    Column {
        OutlinedTextField(
            value = authState.userName,
            onValueChange = { userName ->
                updateAuth(
                    AuthState.Basic(
                        userName, authState.password
                    )
                )
            },
            label = { Text(text = stringResource(R.string.user_name)) },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = authState.password,
            onValueChange = { password ->
                updateAuth(
                    AuthState.Basic(
                        authState.userName, password
                    )
                )
            },
            label = { Text(text = stringResource(R.string.password)) },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 15.dp)
                .fillMaxWidth()
        )
    }
}