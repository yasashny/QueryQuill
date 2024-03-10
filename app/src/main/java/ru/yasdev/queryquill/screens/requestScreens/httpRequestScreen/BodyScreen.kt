package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
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
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.BodyScreenAlertDialog
import ru.yasdev.queryquill.components.ChipGroupSingleLine
import ru.yasdev.queryquill.components.editableList
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.reflect.KFunction1


fun LazyListScope.bodyScreen(
    requestModel: RequestModel,
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    bodyState: MutableState<Int>
) {
    when (requestModel.body) {
        Body.NoBody -> {
            bodyState.value = 0
        }

        is Body.Text -> {
            bodyState.value = 1
        }

        is Body.FormUrlEncoded -> {
            bodyState.value = 2
        }

        Body.MultipartForm -> {
            bodyState.value = 3
        }

        is Body.BinaryFile -> {
            bodyState.value = 4
        }
    }

    item {
        Row {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, bottom = 15.dp, top = 15.dp)
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
                        when (requestModel.body) {
                            is Body.Text -> {
                                if ((requestModel.body as Body.Text).text == "") {
                                    changeBodyType(
                                        updateRequest = updateRequest,
                                        index
                                    )
                                } else {
                                    openDialog.value = Pair(true, index)
                                }
                            }

                            is Body.FormUrlEncoded -> {
                                if ((requestModel.body as Body.FormUrlEncoded).list == listOf(
                                        ListItem(
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

                            is Body.NoBody -> {
                                changeBodyType(
                                    updateRequest = updateRequest,
                                    index
                                )
                            }

                            is Body.MultipartForm -> {
                                changeBodyType(
                                    updateRequest = updateRequest,
                                    index
                                )
                            }

                            is Body.BinaryFile -> {
                                changeBodyType(
                                    updateRequest = updateRequest,
                                    index
                                )
                            }
                        }


                    }

                }
            }

        }
    }

    when (requestModel.body) {
        is Body.Text -> {
            item {
                OutlinedTextField(
                    value = (requestModel.body as Body.Text).text,
                    onValueChange = { updateRequest(UpdateHttpRequestModel.Body(Body.Text(it))) },
                    label = @Composable { Text(text = "Json/XML") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                        .heightIn(min = 150.dp)
                )
            }
        }

        is Body.FormUrlEncoded -> {
            editableList(
                items = (requestModel.body as Body.FormUrlEncoded).list,
                onValueChanged = {
                    updateRequest(UpdateHttpRequestModel.Body(Body.FormUrlEncoded(it)))
                })
        }

        Body.NoBody -> {

        }

        Body.MultipartForm -> {
            item {
                Text(text = "MultipartForm")
            }

        }

        is Body.BinaryFile -> {

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
                            updateRequest(UpdateHttpRequestModel.Body(Body.BinaryFile(selectedUri)))
                        }
                    })
                Button(onClick = { getContent.launch(arrayOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) }) {
                    Text("Selected File: ${(requestModel.body as Body.BinaryFile).uri.path}")
                }
            }
//--------------------------------SAMPLE---------------------------------------------------------
            item {
                val flag = remember { mutableStateOf(false) }
                Button(onClick = { flag.value = true }) {}
                Text(text = (requestModel.body as Body.BinaryFile).uri.toString())
                if (flag.value){
                    readTextFromUri(uri = (requestModel.body as Body.BinaryFile).uri, context = LocalContext.current)
                }
            }
//--------------------------------SAMPLE---------------------------------------------------------
        }
    }


}

private fun changeBodyType(
    updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,
    index: Int
) {
    when (index) {
        0 -> {
            updateRequest(UpdateHttpRequestModel.Body(Body.NoBody))
        }

        1 -> {
            updateRequest(UpdateHttpRequestModel.Body(Body.Text("")))
        }

        2 -> {
            updateRequest(
                UpdateHttpRequestModel.Body(
                    Body.FormUrlEncoded(
                        listOf(
                            ListItem(
                                "",
                                ""
                            )
                        )
                    )
                )
            )
        }

        3 -> {
            updateRequest(UpdateHttpRequestModel.Body(Body.MultipartForm))
        }

        4 -> {
            updateRequest(UpdateHttpRequestModel.Body(Body.BinaryFile(Uri.EMPTY)))
        }
    }
}


//------------------SAMPLE----------------------------
@Composable
@Throws(IOException::class)
private fun readTextFromUri(uri: Uri, context: Context) {
    val stringBuilder = StringBuilder()
    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            var line: String? = reader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = reader.readLine()
            }
        }
    }
    Text(text = stringBuilder.toString())
}
//--------------------SAMPLE--------------------------