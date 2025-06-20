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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.feature.request.R

@Composable
internal fun AuthScreenBasic(
    modifier: Modifier = Modifier,
    authState: AuthState.Basic,
    onAuthChange: (AuthState.Basic) -> Unit
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = authState.userName,
            onValueChange = { userName ->
                onAuthChange(
                    authState.copy(userName = userName)
                )
            },
            label = { Text(text = stringResource(R.string.user_name)) },
            modifier = Modifier
                .padding(horizontal = Dimens.medium)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = Dimens.small))
        OutlinedTextField(
            value = authState.password,
            onValueChange = { onAuthChange(authState.copy(password = it)) },
            label = { Text(stringResource(R.string.password)) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) painterResource(R.drawable.visibility_off)
                else painterResource(R.drawable.visibility)

                IconButton(
                    modifier = Modifier.padding(end = Dimens.small, top = 2.dp),
                    onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = icon, contentDescription = null)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .padding(horizontal = Dimens.medium)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAuthScreenBasic() {
    QueryQuillTheme {
        AuthScreenBasic(
            authState = AuthState.Basic("", ""), onAuthChange = {})
    }
}