package com.yas.queryquill.screens.requestScreens.httpRequestScreen.body

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
import com.yas.domain.requestsDb.states.BasicState
import com.yas.domain.requestsDb.states.BodyState
import com.yas.queryquill.components.BinaryFileElement
import com.yas.queryquill.components.ChangeTypeAlertDialog
import com.yas.queryquill.components.ChipGroup
import com.yas.queryquill.components.editableList
import com.yas.queryquill.screens.requestScreens.httpRequestScreen.body.multipartForm.bodyScreenMultipartForm
import com.yas.queryquill.screens.requestScreens.httpRequestScreen.body.text.BodyScreenText


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
                    mutableStateOf(Pair(false, bodyState as BasicState))
                }
                if (openDialog.value.first) {
                    ChangeTypeAlertDialog(openDialog, title = "body"){basicState ->
                        updateRequest(basicState as BodyState)
                    }
                }
                ChipGroup(
                    currentState = bodyState, options = listOf(
                        BodyState.NoBody,
                        BodyState.Text.default(),
                        BodyState.FormUrlEncoded.default(),
                        BodyState.MultipartForm.default(),
                        BodyState.BinaryFile.default()
                    )
                ) { newState ->
                    if (bodyState::class != newState::class) {
                        if (bodyState.isDefault()) {
                            updateRequest(newState as BodyState)
                        } else {
                            openDialog.value = Pair(true, newState as BodyState)
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
                BinaryFileElement(currentState = bodyState){uri, fileName ->
                    updateRequest(BodyState.BinaryFile(uri, fileName))
                }
            }
        }
    }
}