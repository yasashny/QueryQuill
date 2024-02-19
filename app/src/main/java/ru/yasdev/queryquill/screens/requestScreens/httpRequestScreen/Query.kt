package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.yasdev.domain.utils.ListItem
import ru.yasdev.queryquill.components.EditableList

@Composable
fun Query(){
    EditableList(items = listOf(ListItem("qqq", "qqq")))
}