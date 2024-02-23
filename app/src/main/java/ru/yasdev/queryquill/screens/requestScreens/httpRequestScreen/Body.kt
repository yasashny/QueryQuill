package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.components.EditableList
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect

@Composable
fun Body(viewModel: MainActivityViewModel) {
    val body = viewModel.requestModel.collectAsState().value.body
    Column {
        val selectedIndex = remember { mutableStateOf(0) }
        Row {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    //.background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(bottom = 15.dp, top = 15.dp)
                //.background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                val options = listOf("Text", "Structured")
                SegmentedButtonSingleSelect(selectedIndex, options)
            }

        }
        when (selectedIndex.value) {
            0 -> {
                OutlinedTextField(value = "",
                    onValueChange = {},
                    label = @Composable { Text(text = "Json/XML") },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                )
            }

            1 -> {
                EditableList(items = listOf(
                    ListItem("qee", "dkhfjdsf"),
                    ListItem("qee", "dkhfjdsf"),
                    ListItem("qee", "dkhfjdsf")
                ), onValueChanged = {})
            }
        }

    }

}