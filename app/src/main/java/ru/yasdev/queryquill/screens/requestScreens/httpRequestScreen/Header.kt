package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.UpdateHttpRequestModel
import ru.yasdev.queryquill.components.EditableList

@Composable
fun Header(viewModel: MainActivityViewModel){
    val headers = viewModel.requestModel.collectAsState().value.header
    EditableList(items = headers, onValueChanged = {
        viewModel.updateHttpRequest(UpdateHttpRequestModel.Header(it))
    })
}