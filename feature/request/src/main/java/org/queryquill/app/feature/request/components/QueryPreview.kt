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

package org.queryquill.app.feature.request.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.KeyValue

@Composable
fun QueryPreview(
    url: String, query: List<KeyValue>, modifier: Modifier = Modifier
) {
    val preview by remember(url, query) { mutableStateOf(buildPreview(url, query)) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(15.dp)
    ) {
        Text(
            text = preview, color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

private fun buildPreview(url: String, query: List<KeyValue>): String {
    val base = if (url.startsWith("http://", true) || url.startsWith("https://", true)) url
    else "http://$url"

    val uri = base.toUri()
    val builder = uri.buildUpon().clearQuery()

    query.filterNot { it.key.isBlank() && it.value.isBlank() }
        .forEach { builder.appendQueryParameter(it.key, it.value) }

    return builder.build().toString()
}

@Preview(showBackground = true)
@Composable
private fun PreviewQueryPreview() {
    QueryQuillTheme {
        QueryPreview(
            url = "example.com", query = listOf(
                KeyValue("param1", "value1"), KeyValue("param2", "value2")
            ), modifier = Modifier.padding(15.dp)
        )
    }
}