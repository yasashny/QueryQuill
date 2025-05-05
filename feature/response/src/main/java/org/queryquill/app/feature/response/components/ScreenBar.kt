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

package org.queryquill.app.feature.response.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.feature.response.R
import org.queryquill.app.feature.response.preview.saveFileLauncher
import java.io.File

@Composable
internal fun ScreenBar(status: String, time: String, contentLength: String, file: File) {

    val saveFile = saveFileLauncher(file)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 18.dp, end = 15.dp, bottom = 15.dp)
            .height(56.dp)
            .border(
                1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(4.dp)
            )

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = status,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
            )
            VerticalDivider(color = MaterialTheme.colorScheme.outline)
            Text(
                text = stringResource(R.string.ms, time),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(10.dp)
            )
            VerticalDivider(color = MaterialTheme.colorScheme.outline)
            Text(
                text = stringResource(R.string.bytes, contentLength),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(10.dp)
            )
            VerticalDivider(color = MaterialTheme.colorScheme.outline)
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = { saveFile.launch(file.name) },
                    enabled = file.name != ResponseModel.DEFAULT_FILE_NAME
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.download),
                        contentDescription = null,
                        Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}