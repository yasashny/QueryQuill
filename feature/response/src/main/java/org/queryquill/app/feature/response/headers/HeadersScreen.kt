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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.KeyValue

@Composable
internal fun HeadersScreen(headers: ImmutableList<KeyValue>) {

    LazyColumn {
        items(headers.list) { item ->
            SelectionContainer {
                Column {
                    HorizontalDivider()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(IntrinsicSize.Min)
                    ) {
                        Row {
                            Text(
                                text = item.key,
                                Modifier
                                    .padding(15.dp)
                                    .weight(1f)
                            )
                            VerticalDivider()
                            Text(
                                text = item.value,
                                Modifier
                                    .padding(15.dp)
                                    .weight(1f)
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}