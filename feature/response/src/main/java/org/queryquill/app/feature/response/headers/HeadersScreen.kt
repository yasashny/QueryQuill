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

package org.queryquill.app.feature.response.headers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.KeyValue

@Composable
internal fun HeadersScreen(headers: List<KeyValue>) {
    LazyColumn {
        itemsIndexed(headers) { index, item ->
            SelectionContainer {
                Column {
                    HorizontalDivider(thickness = 2.dp)
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(IntrinsicSize.Min)
                            .background(
                                color = if (index % 2 == 0) {
                                    MaterialTheme.colorScheme.surfaceContainerHighest
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainer
                                }
                            )
                    ) {
                        Row {
                            Text(
                                text = item.key, Modifier
                                    .padding(Dimens.medium)
                                    .weight(1f)
                            )
                            VerticalDivider()
                            Text(
                                text = item.value, Modifier
                                    .padding(Dimens.medium)
                                    .weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun HeadersScreenPreview() {
    val sample = listOf(
        KeyValue("Accept", "application/json"),
        KeyValue("Content-Type", "application/json"),
        KeyValue("Authorization", "Bearer eyJh…"),
        KeyValue("Cache-Control", "no-cache"),
        KeyValue("User-Agent", "QueryQuill/1.0")
    )
    QueryQuillTheme {
        HeadersScreen(headers = sample)
    }
}