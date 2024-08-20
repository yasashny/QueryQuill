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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.BasicState
import com.yas.model.BodyState
import com.yas.model.ImmutableList
import com.yas.model.ImmutableUri
import com.yas.request.R
import com.yas.request.alertDialog.ChangeTypeDialog
import com.yas.request.body.multipartForm.bodyScreenMultipartForm
import com.yas.request.body.text.BodyScreenText
import com.yas.request.components.BinaryFileElement
import com.yas.request.components.ChipGroup
import com.yas.request.components.editableList
import java.io.File
import java.net.URI


internal fun LazyListScope.bodyScreen(
    bodyState: BodyState,
    getTextFileUri: (textFileName: String) -> URI,
    requestId: Long,
    navigateToEditor: (textFileName: String, languageType: String) -> Unit,
    updateRequest: (BodyState) -> Unit
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
                    var openChangeTypeDialog by remember {
                        mutableStateOf(Pair(false, bodyState as BasicState))
                    }
                    if (openChangeTypeDialog.first) {
                        ChangeTypeDialog(title = stringResource(R.string.body), onDismiss = {
                            openChangeTypeDialog = Pair(false, openChangeTypeDialog.second)
                        }, onConfirm = {
                            when (bodyState) {
                                is BodyState.BinaryFile -> {}
                                is BodyState.FormUrlEncoded -> {}
                                is BodyState.MultipartForm -> {}
                                BodyState.NoBody -> {}
                                is BodyState.Text -> {
                                    val file = File(getTextFileUri(bodyState.textFileName))
                                    file.delete()
                                }
                            }
                            updateRequest(openChangeTypeDialog.second as BodyState)
                            openChangeTypeDialog = Pair(false, openChangeTypeDialog.second)
                        })
                    }
                    ChipGroup(
                        currentState = bodyState.toEnum(), options = ImmutableList(
                            listOf(
                                EnumBodyState.NoBody,
                                EnumBodyState.Text,
                                EnumBodyState.FormUrlEncoded,
                                EnumBodyState.MultipartForm,
                                EnumBodyState.BinaryFile
                            )
                        )
                    ) { newEnumState: EnumBodyState ->
                        val newState = newEnumState.toBodyState(requestId)
                        if (bodyState != newState) {
                            if (when (bodyState) {
                                    is BodyState.BinaryFile -> bodyState == BodyState.BinaryFile.default()
                                    is BodyState.FormUrlEncoded -> bodyState == BodyState.FormUrlEncoded.default()
                                    is BodyState.MultipartForm -> bodyState == BodyState.MultipartForm.default()
                                    BodyState.NoBody -> true
                                    is BodyState.Text -> {
                                        val file = File(getTextFileUri(bodyState.textFileName))
                                        file.length() == 0L
                                    }
                                }
                            ) {
                                updateRequest(newState)
                            } else {
                                openChangeTypeDialog = Pair(true, newState)
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