package org.queryquill.app.feature.request.components

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.BasicBinaryFile
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.ImmutableUri
import org.queryquill.app.data.requests.utils.getMIMEType
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.alertDialog.ChangeContentTypeDialog
import org.queryquill.app.feature.request.utils.fileNameByUri

@Composable
internal fun BinaryFileElement(
    currentState: BasicBinaryFile,
    updateRequest: (selectedUri: Uri, fileName: String, isChangeType: Boolean, contentType: String) -> Unit,
    isContentTypeInHeaders: (String) -> Boolean
) {
    val context = LocalContext.current


    var openChangeContentTypeDialog by remember {
        mutableStateOf(Triple(false, "", BodyState.BinaryFile.default()))
    }
    if (openChangeContentTypeDialog.first) {
        ChangeContentTypeDialog(newContentType = openChangeContentTypeDialog.second, onDismiss = {
            updateRequest(
                openChangeContentTypeDialog.third.uri.uri,
                openChangeContentTypeDialog.third.fileName,
                false,
                openChangeContentTypeDialog.second
            )
            openChangeContentTypeDialog = Triple(
                false, openChangeContentTypeDialog.second, openChangeContentTypeDialog.third
            )
        }, onConfirm = {
            updateRequest(
                openChangeContentTypeDialog.third.uri.uri,
                openChangeContentTypeDialog.third.fileName,
                true,
                openChangeContentTypeDialog.second
            )
            openChangeContentTypeDialog = Triple(
                false, openChangeContentTypeDialog.second, openChangeContentTypeDialog.third
            )
        })
    }


    val getContent =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument(),
            onResult = { uri: Uri? ->
                uri?.let { selectedUri ->
                    val contentResolver = context.contentResolver
                    val takeFlags: Int =
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    contentResolver.takePersistableUriPermission(selectedUri, takeFlags)
                    if (!isContentTypeInHeaders(getMIMEType(context, selectedUri))) {
                        openChangeContentTypeDialog = Triple(
                            true, getMIMEType(context, selectedUri), BodyState.BinaryFile(
                                ImmutableUri(selectedUri),
                                fileNameByUri(contentResolver, selectedUri)
                            )
                        )
                    } else {
                        updateRequest(
                            selectedUri,
                            fileNameByUri(contentResolver, selectedUri),
                            false,
                            getMIMEType(context, uri)
                        )
                    }
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
                text = if (currentState.uri.uri == Uri.EMPTY) {
                    stringResource(R.string.select_file)
                } else {
                    currentState.fileName
                },
            )
            IconButton(modifier = Modifier, onClick = {
                updateRequest(
                    Uri.EMPTY, "", false, "application/octet-stream"
                )
            }, enabled = currentState.uri.uri != Uri.EMPTY) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        }
    }
}