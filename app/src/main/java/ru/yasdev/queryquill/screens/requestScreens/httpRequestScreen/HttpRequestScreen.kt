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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HttpRequestScreen(viewModel: MainActivityViewModel) {

    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = { /*TODO*/ }, icon = { Icon(
            imageVector = Icons.AutoMirrored.Outlined.Send,
            contentDescription = ""
        )}, text = { Text(text = "Send request")}, modifier = Modifier.padding(end = 15.dp))
    }) {
        Column {
            Row(
                Modifier
                    //.background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(15.dp)
                //.background(MaterialTheme.colorScheme.surfaceContainer)
            ){
                DynamicSelectTextField(
                    selectedValue = "GET",
                    options = listOf("GET", "POST"),
                    label = "Type",
                    onValueChangedEvent = {}, modifier = Modifier.weight(1f)
                )

                OutlinedTextField(value = "", onValueChange = {}, label = @Composable{ Text(text = "Url")}, modifier = Modifier
                    .weight(2f)
                    .padding(start = 15.dp))
            }
            val selectedIndex = remember { mutableStateOf(0) }
            Row{
                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    //.background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(bottom = 15.dp)
                    //.background(MaterialTheme.colorScheme.surfaceContainer)
                ){
                    val options = listOf("Body", "Auth", "Header", "Query")
                    SegmentedButtonSingleSelect(selectedIndex, options)
                }

            }
            Box (
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant))

            Box(modifier = Modifier.fillMaxSize()){
                when(selectedIndex.value){
                    0 -> {
                        Body()}
                    1 -> {
                        Auth()}
                    2 -> {
                        Header()}
                    3 -> {
                        Query()}
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
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
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
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTab(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        FilterChip(selected = true, onClick = { /*TODO*/ }, label = { "djfkdjf" },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth())

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}


@Composable
fun FilterChipSample() {
    var selected by remember { mutableStateOf(false) }
    FilterChip(
        selected = selected,
        onClick = { selected = !selected },
        label = { Text("Filter chip") },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}