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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.utils.vibration
import org.queryquill.app.feature.request.R

@Composable
internal fun ScreenBar(
    modifier: Modifier = Modifier,
    type: HttpType,
    url: String,
    onTypeChange: (HttpType) -> Unit,
    onUrlChange: (String) -> Unit,
    sendRequest: () -> Unit
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Row(
            Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DynamicSelectTextField(
                selectedValue = type,
                options = HttpType.entries,
                label = stringResource(id = R.string.type),
                modifier = Modifier.weight(1.5f)
            ) { httpType ->
                onTypeChange(httpType)
            }
            Button(
                modifier = Modifier
                    .width(140.dp)
                    .padding(start = 15.dp, top = 7.dp)
                    .height(56.dp),
                onClick = {
                    vibration(context)
                    sendRequest()
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Send,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(stringResource(R.string.send), fontSize = 15.sp)
                }
            }
        }
        OutlinedTextField(
            value = url,
            onValueChange = { onUrlChange(it) },
            label = { Text(text = stringResource(R.string.url)) },
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenBar() {
    QueryQuillTheme {
        ScreenBar(
            type = HttpType.GET,
            url = "https://example.com",
            onTypeChange = {},
            onUrlChange = {},
            sendRequest = {})
    }
}