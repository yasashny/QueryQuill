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

package org.queryquill.app.feature.new_transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.utils.vibration

@Composable
fun NewTransactionScreen() {
    val imageVector = painterResource(id = R.drawable.logo_add_request)
    var openAddTransactionDialog by remember { mutableStateOf(false) }

    if (openAddTransactionDialog) {
        AddTransactionDialog(onDismiss = { openAddTransactionDialog = false })
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        AnimatedLogo(imageVector)
        Button(
            onClick = {
                vibration(context)
                openAddTransactionDialog = true
            }, modifier = Modifier
                .padding(top = 50.dp)
                .size(width = 229.dp, height = 53.dp)
        ) {
            Text(
                text = stringResource(R.string.create_new_request),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = Dimens.medium)
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewNewTransactionScreen() {
    QueryQuillTheme {
        NewTransactionScreen()
    }
}