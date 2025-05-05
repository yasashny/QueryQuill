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

package org.queryquill.app.feature.cookie

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.QueryQuillTheme

@Composable
internal fun CookieListItem(
    modifier: Modifier = Modifier, item: CookieModel, index: Int, onEvent: (UpdateCookie) -> Unit
) {
    OutlinedCard(modifier = modifier.animateContentSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 15.dp, top = 12.dp, end = 15.dp, bottom = 15.dp)
        ) {
            OutlinedTextField(
                value = item.cookie,
                onValueChange = { newText ->
                    onEvent(
                        UpdateCookie.Update(
                            index, CookieModel(item.id, newText)
                        )
                    )
                },
                label = { Text(text = stringResource(R.string.cookie)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag("cookie_input")
            )
            FilledTonalIconButton(
                onClick = { onEvent(UpdateCookie.Delete(index)) },
                modifier = Modifier
                    .padding(start = 15.dp)
                    .testTag("delete_cookie")

            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete, contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCookieListItem() {
    QueryQuillTheme {
        CookieListItem(
            item = CookieModel(id = 1, cookie = "Sample Cookie"), index = 0, onEvent = {})
    }
}