package com.yas.queryquill.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yas.model.BasicBinaryFile
import com.yas.queryquill.utils.fileNameByUri

@Composable
fun BinaryFileElement(
    currentState: BasicBinaryFile, updateRequest: (selectedUri: Uri, fileName: String) -> Unit
) {
    val context = LocalContext.current
    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let { selectedUri ->
                val contentResolver = context.contentResolver
                val takeFlags: Int =
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                contentResolver.takePersistableUriPermission(selectedUri, takeFlags)
                updateRequest(selectedUri, fileNameByUri(context.contentResolver, selectedUri))
            }
        })
    OutlinedCard(
        Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clickable {
                getContent.launch(arrayOf("*/*"))
            },
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f),
                text = if (currentState.uri == Uri.EMPTY) {
                    "Select file"
                } else {
                    currentState.fileName
                },
            )
            IconButton(modifier = Modifier, onClick = {
                updateRequest(
                    Uri.EMPTY, ""
                )
            }, enabled = currentState.uri != Uri.EMPTY) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        }
    }
}