package org.queryquill.app.feature.request.body

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.alertDialog.ChangeTypeDialog
import org.queryquill.app.feature.request.body.multipartForm.bodyScreenMultipartForm
import org.queryquill.app.feature.request.body.text.BodyScreenText
import org.queryquill.app.feature.request.components.BinaryFileElement
import org.queryquill.app.feature.request.components.ChipGroup
import org.queryquill.app.feature.request.components.editableList
import java.io.File
import java.net.URI


internal fun LazyListScope.bodyScreen(
    bodyState: BodyState,
    getTextFileUri: (textFileName: String) -> URI,
    navigateToEditor: (textFileName: String, languageType: String) -> Unit,
    changeBodyType: (EnumBodyState) -> Unit,
    requestId: Long,
    updateTextType: (TextType) -> Unit,
    updateFormUrlEncoded: (List<KeyValue>) -> Unit,
    updateMultipartForm: (List<MultipartFormState>) -> Unit,
    updateBinaryFile: (selectedUri: Uri, fileName: String, isChangeType: Boolean, contentType: String) -> Unit,
    isContentTypeInHeaders: (String) -> Boolean
) {
    item {
        Column {

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 15.dp)
            ) {
                var openChangeTypeDialog by remember {
                    mutableStateOf(Pair(false, bodyState.toEnum()))
                }
                if (openChangeTypeDialog.first) {
                    ChangeTypeDialog(title = stringResource(R.string.body), onDismiss = {
                        openChangeTypeDialog = Pair(false, openChangeTypeDialog.second)
                    }, onConfirm = {
                        changeBodyType(openChangeTypeDialog.second)
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
                            changeBodyType(newEnumState)
                        } else {
                            openChangeTypeDialog = Pair(true, newEnumState)
                        }
                    }
                }
            }

            HorizontalDivider()
        }

    }
    when (bodyState) {
        is BodyState.Text -> {
            item {
                BodyScreenText(
                    bodyState = bodyState,
                    updateTextType = updateTextType,
                    navigateToEditor = navigateToEditor
                )
            }
        }

        is BodyState.FormUrlEncoded -> {
            editableList(items = bodyState.list.list) { keyValueList ->
                updateFormUrlEncoded(keyValueList)
            }
        }

        BodyState.NoBody -> {}
        is BodyState.MultipartForm -> {
            bodyScreenMultipartForm(
                items = bodyState.multipart.list, updateMultipartForm = updateMultipartForm
            )
        }

        is BodyState.BinaryFile -> {
            item {
                BinaryFileElement(
                    currentState = bodyState,
                    updateRequest = updateBinaryFile,
                    isContentTypeInHeaders = isContentTypeInHeaders
                )
            }
        }
    }
}