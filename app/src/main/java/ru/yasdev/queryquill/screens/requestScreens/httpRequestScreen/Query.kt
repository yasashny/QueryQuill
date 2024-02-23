package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.runtime.Composable
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.components.EditableList

@Composable
fun Query(viewModel: MainActivityViewModel){
    EditableList(items = listOf(ListItem("qqq", "qqq")), onValueChanged = {})
}