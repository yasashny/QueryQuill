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
import androidx.compose.foundation.layout.Arrangement
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
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.feature.cookie.util.TestTags

@Composable
internal fun CookieListItem(
    modifier: Modifier = Modifier, item: CookieModel, onEvent: (UpdateCookie) -> Unit
) {
    OutlinedCard(modifier = modifier.animateContentSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                start = Dimens.medium, top = 11.dp, end = Dimens.medium, bottom = Dimens.medium
            )
        ) {
            OutlinedTextField(
                value = item.cookie,
                onValueChange = { newText ->
                    onEvent(
                        UpdateCookie.Update(
                            item.copy(cookie = newText)
                        )
                    )
                },
                label = { Text(text = stringResource(R.string.cookie)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .testTag(TestTags.CookieList.COOKIE_INPUT)
            )
            FilledTonalIconButton(
                onClick = { onEvent(UpdateCookie.Delete(item.id)) },
                modifier = Modifier
                    .padding(start = Dimens.medium)
                    .testTag(TestTags.CookieList.DELETE_COOKIE)

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
            item = CookieModel("Sample Cookie"), onEvent = {})
    }
}