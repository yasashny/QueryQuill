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

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.FileInfo
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.utils.fileNameByUri

@Composable
internal fun BinaryFileElement(
    modifier: Modifier = Modifier,
    currentState: FileInfo,
    onFileChange: (uri: Uri, fileName: String) -> Unit
) {
    val context = LocalContext.current
    val contentResolver = context.contentResolver

    val filePickerLauncher = rememberBinaryFilePicker(
        onFilePicked = { uri ->
            onFileChange(
                uri, fileNameByUri(contentResolver, uri)
            )
        })

    OutlinedCard(
        modifier
            .fillMaxWidth()
            .clickable {
                filePickerLauncher.launch(arrayOf("*/*"))
            },
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .padding(Dimens.medium)
                    .weight(1f),
                text = if (currentState.uri == Uri.EMPTY) {
                    stringResource(R.string.select_file)
                } else {
                    currentState.fileName
                },
            )
            IconButton(modifier = Modifier, onClick = {
                onFileChange(
                    Uri.EMPTY, ""
                )
            }, enabled = currentState.uri != Uri.EMPTY) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        }
    }
}

@Composable
private fun rememberBinaryFilePicker(
    onFilePicked: (Uri) -> Unit
): ManagedActivityResultLauncher<Array<String>, Uri?> {
    val context = LocalContext.current
    val onFilePickedState by rememberUpdatedState(onFilePicked)
    return rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri ?: return@rememberLauncherForActivityResult
        context.contentResolver.takePersistableUriPermission(
            uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        onFilePickedState(uri)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBinaryFileElement() {
    val dummyFileInfo = BodyState.BinaryFile(
        uri = Uri.EMPTY,
        fileName = "example.txt",
    )
    QueryQuillTheme {
        BinaryFileElement(
            currentState = dummyFileInfo,
            onFileChange = { _, _ -> },
            modifier = Modifier.padding(Dimens.medium)
        )
    }
}