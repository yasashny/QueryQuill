package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.body.multipartForm

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.MultipartFormType
import ru.yasdev.domain.requestsDb.states.MultipartFormState
import ru.yasdev.queryquill.components.BinaryFileElement
import ru.yasdev.queryquill.components.DynamicSelectTextField
import ru.yasdev.queryquill.components.KeyValueItem


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
                .padding(start = 15.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DynamicSelectTextField(
                selectedValue = when (multipartFormState) {
                    is MultipartFormState.BinaryFile -> MultipartFormType.FILE
                    is MultipartFormState.Text -> MultipartFormType.TEXT
                }, options = listOf(
                    MultipartFormType.FILE, MultipartFormType.TEXT
                ), label = "Type", modifier = Modifier
                    .weight(1f)
                    .width(200.dp)
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
            IconButton(
                onClick = { deleteItem() },
                enabled = deleteButtonEnabled(),
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        }
        when (multipartFormState) {
            is MultipartFormState.BinaryFile -> {
                BinaryFileElement(currentState = multipartFormState) { uri ->
                    onTextChanged(MultipartFormState.BinaryFile(uri), true)
                }
            }

            is MultipartFormState.Text -> {
                KeyValueItem(
                    keyValue = multipartFormState.keyValue, onTextChanged = {
                        onTextChanged(MultipartFormState.Text(it), true)
                    }, isDeleteButtonVisible = false, modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}