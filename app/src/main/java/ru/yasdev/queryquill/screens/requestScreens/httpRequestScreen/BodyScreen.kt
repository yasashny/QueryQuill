package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.BodyState
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.models.MultipartFormState
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.BodyScreenAlertDialog
import ru.yasdev.queryquill.components.ChipGroupSingleLine
import ru.yasdev.queryquill.components.editableList
import ru.yasdev.queryquill.components.multipartFormList
import kotlin.reflect.KFunction1


fun LazyListScope.bodyScreen(
    requestModel: RequestModel,
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    bodyState: MutableState<Int>
) {
    when (requestModel.bodyState) {
        BodyState.NoBody -> {
            bodyState.value = 0
        }

        is BodyState.Text -> {
            bodyState.value = 1
        }

        is BodyState.FormUrlEncoded -> {
            bodyState.value = 2
        }

        is BodyState.MultipartForm -> {
            bodyState.value = 3
        }

        is BodyState.BinaryFile -> {
            bodyState.value = 4
        }
    }

    item {
        Row {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 15.dp)
            ) {
                val options =
                    listOf("No body", "Text", "Form Url Encoded", "Multiplatform", "Binary file")
                val openDialog = remember {
                    mutableStateOf(Pair(false, 0))
                }
                val isChangeBodyState = remember {
                    mutableStateOf(Pair(false, 0))
                }
                if (openDialog.value.first) {
                    BodyScreenAlertDialog(isChangeBodyState, openDialog)
                }
                if (isChangeBodyState.value.first) {
                    changeBodyType(updateRequest = updateRequest, isChangeBodyState.value.second)
                    isChangeBodyState.value = Pair(false, isChangeBodyState.value.second)
                }
                ChipGroupSingleLine(selectedIndex = bodyState, options = options) { index ->
                    if (bodyState.value != index) {
                        when (requestModel.bodyState) {
                            is BodyState.Text -> {
                                if ((requestModel.bodyState as BodyState.Text).text == "") {
                                    changeBodyType(
                                        updateRequest = updateRequest,
                                        index
                                    )
                                } else {
                                    openDialog.value = Pair(true, index)
                                }
                            }

                            is BodyState.FormUrlEncoded -> {
                                if ((requestModel.bodyState as BodyState.FormUrlEncoded).list == listOf(
                                        KeyValue(
                                            "",
                                            ""
                                        )
                                    )
                                ) {
                                    changeBodyType(
                                        updateRequest = updateRequest,
                                        index
                                    )
                                } else {
                                    openDialog.value = Pair(true, index)
                                }
                            }

                            is BodyState.NoBody -> {
                                changeBodyType(
                                    updateRequest = updateRequest,
                                    index
                                )
                            }

                            is BodyState.MultipartForm -> {
                                changeBodyType(
                                    updateRequest = updateRequest,
                                    index
                                )
                            }

                            is BodyState.BinaryFile -> {
                                if ((requestModel.bodyState as BodyState.BinaryFile).uri == Uri.EMPTY){
                                    changeBodyType(
                                        updateRequest = updateRequest,
                                        index
                                    )
                                }
                                else{
                                    openDialog.value = Pair(true, index)
                                }

                            }
                        }


                    }

                }
            }

        }
    }

    when (requestModel.bodyState) {
        is BodyState.Text -> {
            item {
                OutlinedTextField(
                    value = (requestModel.bodyState as BodyState.Text).text,
                    onValueChange = { updateRequest(UpdateHttpRequestModel.Body(BodyState.Text(it))) },
                    label = @Composable { Text(text = "Json/XML") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                        .heightIn(min = 150.dp)
                )
            }
        }

        is BodyState.FormUrlEncoded -> {
            println("HJKKKKKKKKKKKKKKKKK")
            editableList(
                items = (requestModel.bodyState as BodyState.FormUrlEncoded).list,
                onValueChanged = {
                    updateRequest(UpdateHttpRequestModel.Body(BodyState.FormUrlEncoded(it)))
                })
        }

        BodyState.NoBody -> {

        }

        is BodyState.MultipartForm -> {
            multipartFormList(
                items = (requestModel.bodyState as BodyState.MultipartForm).multipart,
                onValueChanged = {
                    updateRequest(UpdateHttpRequestModel.Body(BodyState.MultipartForm(it)))
                }
            )

        }

        is BodyState.BinaryFile -> {

            item {

                val cxt = LocalContext.current
                val getContent = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.OpenDocument(),
                    onResult = { uri: Uri? ->
                        uri?.let { selectedUri ->
                            val contentResolver = cxt.contentResolver
                            val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            contentResolver.takePersistableUriPermission(selectedUri, takeFlags)
                            updateRequest(UpdateHttpRequestModel.Body(BodyState.BinaryFile(selectedUri)))
                        }
                    })
                
                OutlinedCard(
                    Modifier
                        .padding(start = 15.dp, end = 15.dp)
                        .fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier
                                .padding(15.dp)
                                .weight(1f),
                            text = if ((requestModel.bodyState as BodyState.BinaryFile).uri == Uri.EMPTY) {
                                "No file selected"
                            } else {
                                queryName(LocalContext.current.contentResolver, (requestModel.bodyState as BodyState.BinaryFile).uri)
                            },
                        )
                        IconButton(modifier = Modifier, onClick = { updateRequest(UpdateHttpRequestModel.Body(BodyState.BinaryFile(
                            Uri.EMPTY))) }, enabled = (requestModel.bodyState as BodyState.BinaryFile).uri != Uri.EMPTY) {
                            Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                        }
                    }

                }

                OutlinedButton(modifier = Modifier.padding(15.dp), onClick = { getContent.launch(arrayOf("*/*")) }) {
                    Text("Selected File")
                }
            }
        }
    }


}

private fun changeBodyType(
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    index: Int
) {
    when (index) {
        0 -> {
            updateRequest(UpdateHttpRequestModel.Body(BodyState.NoBody))
        }

        1 -> {
            updateRequest(UpdateHttpRequestModel.Body(BodyState.Text("")))
        }

        2 -> {
            updateRequest(
                UpdateHttpRequestModel.Body(
                    BodyState.FormUrlEncoded(
                        listOf(
                            KeyValue(
                                "",
                                ""
                            )
                        )
                    )
                )
            )
        }

        3 -> {
            updateRequest(UpdateHttpRequestModel.Body(BodyState.MultipartForm(listOf(MultipartFormState.Text(
                KeyValue("", "")
            )))))
        }

        4 -> {
            updateRequest(UpdateHttpRequestModel.Body(BodyState.BinaryFile(Uri.EMPTY)))
        }
    }
}

fun queryName(resolver: ContentResolver, uri: Uri): String {
    val returnCursor = resolver.query(uri, null, null, null, null)!!
    val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name: String = returnCursor.getString(nameIndex)
    returnCursor.close()
    return name
}