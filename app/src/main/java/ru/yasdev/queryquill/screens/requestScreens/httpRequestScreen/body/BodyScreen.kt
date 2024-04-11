package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.body

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.BodyState
import ru.yasdev.queryquill.components.BodyScreenAlertDialog
import ru.yasdev.queryquill.components.editableList
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.body.multipartForm.bodyScreenMultipartForm


fun LazyListScope.bodyScreen(
    bodyState: BodyState, updateRequest: (BodyState) -> Unit
) {
    item {
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 15.dp)
            ) {
                val openDialog = remember {
                    mutableStateOf(Pair(false, bodyState))
                }
                if (openDialog.value.first) {
                    BodyScreenAlertDialog(openDialog, updateRequest)
                }
                BodyChipGroup(bodyState = bodyState) { chipState ->
                    if (bodyState::class != chipState::class) {
                        if (bodyState.isDefault()) {
                            updateRequest(chipState)
                        } else {
                            openDialog.value = Pair(true, chipState)
                        }
                    }
                }
            }
        }
    }
    when (bodyState) {
        is BodyState.Text -> {
            item {
                BodyScreenText(bodyState = bodyState, updateRequest = updateRequest)
            }
        }

        is BodyState.FormUrlEncoded -> {
            editableList(items = bodyState.list) { keyValueList ->
                updateRequest(BodyState.FormUrlEncoded(keyValueList))
            }
        }

        BodyState.NoBody -> {}
        is BodyState.MultipartForm -> {
            bodyScreenMultipartForm(items = bodyState.multipart, updateRequest)
        }

        is BodyState.BinaryFile -> {
            item {
                BodyScreenBinaryFile(bodyState = bodyState, updateRequest)
            }
        }
    }
}