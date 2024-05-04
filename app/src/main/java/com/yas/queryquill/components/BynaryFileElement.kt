package com.yas.queryquill.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yas.domain.requestsDb.states.BasicBinaryFile
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
            .padding(start = 15.dp, end = 15.dp, top = 15.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .padding(15.dp)
                    .weight(1f),
                text = if (currentState.uri == Uri.EMPTY) {
                    "No file selected"
                } else {
                    currentState.fileName
                },
            )
            IconButton(modifier = Modifier, onClick = {
                updateRequest(
                    Uri.EMPTY,
                    ""
                )
            }, enabled = currentState.uri != Uri.EMPTY) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
            }
        }
    }
    OutlinedButton(
        modifier = Modifier.padding(15.dp),
        onClick = { getContent.launch(arrayOf("*/*")) }) {
        Text("Selected File")
    }
}