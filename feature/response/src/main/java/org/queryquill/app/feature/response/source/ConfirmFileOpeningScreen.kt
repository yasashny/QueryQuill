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

package org.queryquill.app.feature.response.source

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.feature.response.R

@Composable
internal fun ConfirmFileOpeningScreen(onConfirm: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.padding(Dimens.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.a_file_larger_than_5_mb_is_hidden_for_performance_reasons),
                Modifier.padding(Dimens.medium),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { onConfirm() }, modifier = Modifier.padding(top = Dimens.medium)
            ) {
                Text(text = stringResource(R.string.show_anyway))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewConfirmFileOpeningScreen() {
    QueryQuillTheme {
        ConfirmFileOpeningScreen(onConfirm = {})
    }
}