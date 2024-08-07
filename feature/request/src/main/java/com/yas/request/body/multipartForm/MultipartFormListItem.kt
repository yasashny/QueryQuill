package com.yas.request.body.multipartForm

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.model.KeyValue
import com.yas.model.MultipartFormType
import com.yas.model.MultipartFormState
import com.yas.request.BinaryFileElement
import com.yas.request.DynamicSelectTextField


@Composable
fun MultipartFormListItem(
    multipartFormState: MultipartFormState,
    onTextChanged: (newState: MultipartFormState, isAddNewElement: Boolean) -> Unit,
    deleteItem: () -> Unit,
    deleteButtonEnabled: () -> Boolean
) {
    OutlinedCard(
        Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            com.yas.request.DynamicSelectTextField(
                selectedValue = when (multipartFormState) {
                    is MultipartFormState.BinaryFile -> MultipartFormType.FILE
                    is MultipartFormState.Text -> MultipartFormType.TEXT
                }, options = listOf(
                    MultipartFormType.TEXT, MultipartFormType.FILE
                ), label = "Type", modifier = Modifier.weight(1f)
            ) { multipartFormType ->
                when (multipartFormType) {
                    MultipartFormType.TEXT -> {
                        onTextChanged(MultipartFormState.Text.default(), false)
                    }

                    MultipartFormType.FILE -> {
                        onTextChanged(MultipartFormState.BinaryFile.default(), false)
                    }
                }
            }
            FilledTonalIconButton(
                onClick = { deleteItem() },
                enabled = deleteButtonEnabled(),
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        }
        when (multipartFormState) {
            is MultipartFormState.BinaryFile -> {
                OutlinedTextField(
                    value = multipartFormState.title,
                    onValueChange = { newTitle ->
                        onTextChanged(
                            MultipartFormState.BinaryFile(
                                uri = multipartFormState.uri,
                                title = newTitle,
                                fileName = multipartFormState.fileName
                            ), true
                        )
                    },
                    label = { Text(text = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )
                com.yas.request.BinaryFileElement(currentState = multipartFormState) { uri, fileName ->
                    onTextChanged(
                        MultipartFormState.BinaryFile(
                            uri = uri, title = multipartFormState.title, fileName = fileName
                        ), true
                    )
                }
            }

            is MultipartFormState.Text -> {
                OutlinedTextField(
                    value = multipartFormState.keyValue.key,
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
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp)

                )
                OutlinedTextField(
                    value = multipartFormState.keyValue.value,
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
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
        }
    }
}