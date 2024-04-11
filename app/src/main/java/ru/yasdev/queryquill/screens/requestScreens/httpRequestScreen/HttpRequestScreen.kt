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
import ru.yasdev.domain.requestsDb.models.BodyState
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.DynamicSelectTextField
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.auth.authScreen
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.body.bodyScreen
import kotlin.reflect.KFunction1

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HttpRequestScreen(
    requestModel: RequestModel, updateRequest: KFunction1<UpdateHttpRequestModel, Unit>
) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { /*TODO*/ }, icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Send, contentDescription = ""
            )
        }, text = { Text(text = "Send request") })
    }) {
        val httpRequestHeaderState = remember { mutableStateOf(0) }
        LazyColumn {
            item { HttpRequestHeader(requestModel, updateRequest, httpRequestHeaderState) }
            when (httpRequestHeaderState.value) {
                0 -> {
                    bodyScreen(requestModel.bodyState) { bodyState ->
                        updateRequest(UpdateHttpRequestModel.Body(bodyState))
                    }
                }

                1 -> {
                    authScreen(requestModel.auth) { authState ->
                        updateRequest(UpdateHttpRequestModel.Auth(authState))
                    }
                }

                2 -> {
                    headerScreen(requestModel, updateRequest)
                }

                3 -> {
                    queryScreen(requestModel, updateRequest)
                }
            }
            item {
                Spacer(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )
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
            selectedValue = requestModel.type, options = listOf(
                HttpType.GET,
                HttpType.POST,
                HttpType.PUT,
                HttpType.PATCH,
                HttpType.OPTIONS,
                HttpType.DELETE,
                HttpType.HEAD
            ), label = "Type", modifier = Modifier.weight(1f)
        ) { httpType ->
            updateRequest(UpdateHttpRequestModel.Type(httpType))
        }
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
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        ) {
            val options = listOf("Body", "Auth", "Header", "Query")
            SegmentedButtonSingleSelect(headerState, options, onClick = { headerState.value = it })
        }

    }
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}




