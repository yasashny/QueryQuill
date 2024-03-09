package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.DynamicSelectTextField
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect
import kotlin.reflect.KFunction1

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HttpRequestScreen(requestModel: RequestModel,
                      updateRequest: KFunction1<UpdateHttpRequestModel, Unit>) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { /*TODO*/ }, icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Send, contentDescription = ""
            )
        }, text = { Text(text = "Send request") })
    }) {
        val httpRequestHeaderState = remember { mutableStateOf(0) }
        val authState = remember { mutableStateOf(0) }
        val bodyState = remember {
            mutableStateOf(when(requestModel.body){
                Body.NoBody -> {0}
                is Body.Text-> {1}
                is Body.FormUrlEncoded -> {2}
                Body.MultipartForm -> {3}
                Body.BinaryFile -> {4}
            })
        }
        LazyColumn {
            item { HttpRequestHeader(requestModel, updateRequest, httpRequestHeaderState) }
            when (httpRequestHeaderState.value) {
                0 -> { bodyScreen(requestModel, updateRequest, bodyState) }

                1 -> { authScreen(requestModel, updateRequest, authState) }

                2 -> { headerScreen(requestModel, updateRequest) }

                3 -> { queryScreen(requestModel, updateRequest) }
            }
            item { 
                Spacer(modifier = Modifier.height(150.dp).fillMaxWidth())
            }
        }
    }
}

@Composable
private fun HttpRequestHeader(
    requestModel: RequestModel,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
    headerState: MutableState<Int>
) {
    Row(
        Modifier.padding(15.dp)
    ) {
        DynamicSelectTextField(
            selectedValue = requestModel.type.name, options = listOf(
                HttpType.GET.name,
                HttpType.POST.name,
                HttpType.PUT.name,
                HttpType.PATCH.name,
                HttpType.OPTIONS.name,
                HttpType.DELETE.name,
                HttpType.HEAD.name
            ), label = "Type", onValueChangedEvent = {
                when (it) {
                    HttpType.GET.name -> { updateRequest(UpdateHttpRequestModel.Type(HttpType.GET)) }
                    HttpType.POST.name -> { updateRequest(UpdateHttpRequestModel.Type(HttpType.POST)) }
                    HttpType.PUT.name -> { updateRequest(UpdateHttpRequestModel.Type(HttpType.PUT)) }
                    HttpType.PATCH.name -> { updateRequest(UpdateHttpRequestModel.Type(HttpType.PATCH)) }
                    HttpType.DELETE.name -> { updateRequest(UpdateHttpRequestModel.Type(HttpType.DELETE)) }
                    HttpType.OPTIONS.name -> { updateRequest(UpdateHttpRequestModel.Type(HttpType.OPTIONS)) }
                    HttpType.HEAD.name -> { updateRequest(UpdateHttpRequestModel.Type(HttpType.HEAD)) }
                }
            }, modifier = Modifier.weight(1f)
        )
        OutlinedTextField(
            value = requestModel.url,
            onValueChange = { updateRequest(UpdateHttpRequestModel.Url(it)) },
            label = @Composable { Text(text = "Url") },
            modifier = Modifier
                .weight(2f)
                .padding(start = 15.dp)
        )
    }
    Row {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        ) {
            val options = listOf("Body", "Auth", "Header", "Query")
            SegmentedButtonSingleSelect(headerState, options, onClick = {headerState.value = it})
        }

    }
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}




