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
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.BodyScreenAlertDialog
import ru.yasdev.queryquill.components.editableList
import kotlin.reflect.KFunction1


fun LazyListScope.bodyScreen(
    bodyState: BodyState, updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
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
                BodyChipGroup(bodyState = bodyState) {
                    if (bodyState::class != it::class) {
                        if (bodyState.isDefault()) {
                            updateRequest(UpdateHttpRequestModel.Body(it))
                        } else {
                            openDialog.value = Pair(true, it)
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
            editableList(items = bodyState.list, onValueChanged = {
                updateRequest(UpdateHttpRequestModel.Body(BodyState.FormUrlEncoded(it)))
            })
        }

        BodyState.NoBody -> {}
        is BodyState.MultipartForm -> {
            bodyScreenMultipartForm(items = bodyState.multipart) {
                updateRequest(UpdateHttpRequestModel.Body(BodyState.MultipartForm(it)))
            }
        }

        is BodyState.BinaryFile -> {
            item {
                BodyScreenBinaryFile(bodyState = bodyState, updateRequest = updateRequest)
            }
        }
    }
}