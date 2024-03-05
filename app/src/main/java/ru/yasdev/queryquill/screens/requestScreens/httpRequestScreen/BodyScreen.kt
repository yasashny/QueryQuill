package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.BodyScreenAlertDialog
import ru.yasdev.queryquill.components.EditableList
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyScreen(requestModel: RequestModel,
               updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
) {
    Column {
        val selectedIndex = remember { mutableStateOf(0) }
        when(requestModel.body){
            is Body.Text -> {
                selectedIndex.value = 0
            }
            is Body.Structured -> {
                selectedIndex.value = 1
            }
        }
        Row {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    //.background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(bottom = 15.dp, top = 15.dp)
                //.background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                val options = listOf("Text", "Structured")
                val openDialog = remember {
                    mutableStateOf(false)
                }
                val flag = remember {
                    mutableStateOf(false)
                }
                if (openDialog.value){
                    BodyScreenAlertDialog(flag, openDialog)
                }
                if (flag.value){
                    ChangeBodyType(requestModel = requestModel, updateRequest = updateRequest)
                    flag.value = false
                }
                SegmentedButtonSingleSelect(selectedIndex = selectedIndex, options = options) {
                    if(selectedIndex.value != it){

                        when (requestModel.body) {
                            is Body.Text -> {
                                if ((requestModel.body as Body.Text).text == ""){
                                    ChangeBodyType(requestModel = requestModel, updateRequest = updateRequest)
                                }
                                else{
                                    openDialog.value = true
                                }
                            }
                            is Body.Structured -> {
                                if ((requestModel.body as Body.Structured).list == listOf(ListItem("", ""))){
                                    ChangeBodyType(requestModel = requestModel, updateRequest = updateRequest)
                                }
                                else{
                                    openDialog.value = true
                                }
                            }
                        }


                    }

                }
            }

        }
        when (selectedIndex.value) {
            0 -> {
                when(requestModel.body){
                    is Body.Text -> {
                        OutlinedTextField(value = (requestModel.body as Body.Text).text,
                            onValueChange = {updateRequest(UpdateHttpRequestModel.Body(Body.Text(it)))},
                            label = @Composable { Text(text = "Json/XML") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                                .heightIn(min = 150.dp)
                        )
                    }
                    is Body.Structured -> {}
                }

            }

            1 -> {
                when(requestModel.body){
                    is Body.Text -> {}
                    is Body.Structured -> {
                        EditableList(items = (requestModel.body as Body.Structured).list, onValueChanged = {
                            updateRequest(UpdateHttpRequestModel.Body(Body.Structured(it)))
                        })
                    }
                }

            }
        }

    }

}
private fun ChangeBodyType(requestModel: RequestModel, updateRequest: KFunction1<UpdateHttpRequestModel, Unit>){
    when (requestModel.body) {
        is Body.Text -> {
            updateRequest(UpdateHttpRequestModel.Body(Body.Structured(listOf(ListItem("", "")))))
        }

        is Body.Structured -> {
            updateRequest(UpdateHttpRequestModel.Body(Body.Text("")))
        }
    }
}