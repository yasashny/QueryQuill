package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect
import kotlin.reflect.KFunction1

@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HttpRequestScreen(requestModel: RequestModel,
                      updateRequest: KFunction1<UpdateHttpRequestModel, Unit>,) {
    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { /*TODO*/ }, icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Send, contentDescription = ""
            )
        }, text = { Text(text = "Send request") }, modifier = Modifier.padding(end = 15.dp))
    }) {
        Column {
            Row(
                Modifier
                    //.background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(15.dp)
                //.background(MaterialTheme.colorScheme.surfaceContainer)
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
            val selectedIndex = remember { mutableStateOf(0) }
            Row {
                Box(
                    contentAlignment = Alignment.Center, modifier = Modifier
                        .fillMaxWidth()
                        //.background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(bottom = 15.dp)
                    //.background(MaterialTheme.colorScheme.surfaceContainer)
                ) {
                    val options = listOf("Body", "Auth", "Header", "Query")
                    SegmentedButtonSingleSelect(selectedIndex, options)
                }

            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedIndex.value) {
                    0 -> {
                        Body(requestModel, updateRequest)
                    }

                    1 -> {
                        Auth(requestModel, updateRequest)
                    }

                    2 -> {
                        Header(requestModel, updateRequest)
                    }

                    3 -> {
                        Query(requestModel, updateRequest)
                    }
                }
            }


        }


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = !expanded }, modifier = modifier
    ) {
        OutlinedTextField(readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(text = { Text(text = option) }, onClick = {
                    expanded = false
                    onValueChangedEvent(option)
                })
            }
        }
    }
}



