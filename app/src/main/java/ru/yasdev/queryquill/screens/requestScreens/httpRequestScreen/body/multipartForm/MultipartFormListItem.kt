package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.body.multipartForm

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.models.MultipartFormState
import ru.yasdev.queryquill.utils.fileNameByUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultipartFormListItem(
    multipartFormState: MultipartFormState,
    onTextChanged: (MultipartFormState, flag: Boolean) -> Unit,
    deleteItem: () -> Unit,
    deleteButtonEnabled: () -> Boolean
) {
    OutlinedCard(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        var expanded by remember { mutableStateOf(false) }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                Modifier
                    .width(200.dp)
                    .weight(1f)
            ) {
                OutlinedTextField(readOnly = true,
                    value = multipartFormState.name,
                    onValueChange = {},
                    label = { Text(text = "Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    val options = listOf("TEXT", "FILE")
                    options.forEach { option: String ->
                        DropdownMenuItem(text = { Text(text = option) }, onClick = {
                            expanded = false
                            when (multipartFormState) {
                                is MultipartFormState.File -> {
                                    if (option != "FILE") {
                                        onTextChanged(
                                            MultipartFormState.Text(KeyValue("", "")), false
                                        )
                                    }
                                }
                                is MultipartFormState.Text -> {
                                    if (option != "TEXT") {
                                        onTextChanged(MultipartFormState.File(Uri.EMPTY), false)
                                    }
                                }
                            }
                        })
                    }
                }
            }
            IconButton(
                onClick = { deleteItem() },
                enabled = deleteButtonEnabled(),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
            }
        }

        when (multipartFormState) {
            is MultipartFormState.File -> {
                val cxt = LocalContext.current
                val getContent =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument(),
                        onResult = { uri: Uri? ->
                            uri?.let { selectedUri ->
                                val contentResolver = cxt.contentResolver
                                val takeFlags: Int =
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                contentResolver.takePersistableUriPermission(selectedUri, takeFlags)
                                onTextChanged(MultipartFormState.File(selectedUri), true)
                            }
                        })

                OutlinedCard(
                    Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier
                                .padding(15.dp)
                                .weight(1f),
                            text = if (multipartFormState.uri == Uri.EMPTY) {
                                "No file selected"
                            } else {
                                fileNameByUri(
                                    LocalContext.current.contentResolver, multipartFormState.uri
                                )
                            },
                        )
                        IconButton(modifier = Modifier, onClick = {
                            onTextChanged(
                                MultipartFormState.File(Uri.EMPTY), true
                            )
                        }, enabled = multipartFormState.uri != Uri.EMPTY) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                        }
                    }

                }

                OutlinedButton(modifier = Modifier.padding(15.dp),
                    onClick = { getContent.launch(arrayOf("*/*")) }) {
                    Text("Selected File")
                }
            }

            is MultipartFormState.Text -> {
                Row {
                    OutlinedTextField(value = multipartFormState.keyValue.key,
                        onValueChange = {
                            onTextChanged(
                                MultipartFormState.Text(
                                    KeyValue(
                                        it, multipartFormState.keyValue.value
                                    )
                                ), true
                            )
                        },
                        label = { Text(text = "Name") },
                        modifier = Modifier
                            .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                            .weight(1f)
                    )
                    OutlinedTextField(value = multipartFormState.keyValue.value,
                        onValueChange = {
                            onTextChanged(
                                MultipartFormState.Text(
                                    KeyValue(
                                        multipartFormState.keyValue.key, it
                                    )
                                ), true
                            )
                        },
                        label = { Text(text = "Value") },
                        modifier = Modifier
                            .padding(15.dp)
                            .weight(2f)
                    )
                }
            }
        }
    }
}