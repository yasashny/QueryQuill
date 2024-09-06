package org.queryquill.app.feature.request.body.multipartForm

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.ImmutableUri
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.MultipartFormType
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.components.BinaryFileElement
import org.queryquill.app.feature.request.components.DynamicSelectTextField


@Composable
internal fun MultipartFormListItem(
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
            DynamicSelectTextField(
                selectedValue = when (multipartFormState) {
                    is MultipartFormState.BinaryFile -> MultipartFormType.FILE
                    is MultipartFormState.Text -> MultipartFormType.TEXT
                }, options = ImmutableList(
                    listOf(
                        MultipartFormType.TEXT, MultipartFormType.FILE
                    )
                ), label = stringResource(R.string.type), modifier = Modifier.weight(1f)
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
                    label = { Text(text = stringResource(R.string.name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )
                BinaryFileElement(currentState = multipartFormState,
                    isContentTypeInHeaders = { true },
                    updateRequest = { selectedUri, fileName, _, _ ->
                        onTextChanged(
                            MultipartFormState.BinaryFile(
                                uri = ImmutableUri(selectedUri),
                                title = multipartFormState.title,
                                fileName = fileName
                            ), true
                        )
                    })
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
                    label = { Text(text = stringResource(id = R.string.name)) },
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
                    label = { Text(text = stringResource(R.string.value)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
        }
    }
}