package com.yas.request.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.BasicState
import com.yas.model.BodyState
import com.yas.model.ImmutableList
import com.yas.model.ImmutableUri
import com.yas.request.R
import com.yas.request.alertDialog.ChangeTypeAlertDialog
import com.yas.request.body.multipartForm.bodyScreenMultipartForm
import com.yas.request.body.text.BodyScreenText
import com.yas.request.components.BinaryFileElement
import com.yas.request.components.ChipGroup
import com.yas.request.components.editableList


internal fun LazyListScope.bodyScreen(
    bodyState: BodyState, navigateToEditor: () -> Unit, updateRequest: (BodyState) -> Unit
) {
    item {
        Column {
            Row {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, top = 15.dp)
                ) {
                    val openDialog = remember {
                        mutableStateOf(Pair(false, bodyState as BasicState))
                    }
                    if (openDialog.value.first) {
                        ChangeTypeAlertDialog(
                            openDialog, title = stringResource(R.string.body)
                        ) { basicState ->
                            updateRequest(basicState as BodyState)
                        }
                    }
                    ChipGroup(
                        currentState = bodyState, options = ImmutableList(
                            listOf(
                                BodyState.NoBody,
                                BodyState.Text.default(),
                                BodyState.FormUrlEncoded.default(),
                                BodyState.MultipartForm.default(),
                                BodyState.BinaryFile.default()
                            )
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
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
        }

    }
    when (bodyState) {
        is BodyState.Text -> {
            item {
                BodyScreenText(
                    bodyState = bodyState,
                    updateRequest = updateRequest,
                    navigateToEditor = navigateToEditor
                )
            }
        }

        is BodyState.FormUrlEncoded -> {
            editableList(items = bodyState.list.list) { keyValueList ->
                updateRequest(BodyState.FormUrlEncoded(ImmutableList(keyValueList)))
            }
        }

        BodyState.NoBody -> {}
        is BodyState.MultipartForm -> {
            bodyScreenMultipartForm(items = bodyState.multipart.list, updateRequest)
        }

        is BodyState.BinaryFile -> {
            item {
                BinaryFileElement(currentState = bodyState) { uri, fileName ->
                    updateRequest(BodyState.BinaryFile(ImmutableUri(uri), fileName))
                }
            }
        }
    }
}