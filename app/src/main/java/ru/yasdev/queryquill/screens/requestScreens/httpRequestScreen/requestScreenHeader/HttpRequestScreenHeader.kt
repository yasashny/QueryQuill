package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.requestScreenHeader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.queryquill.components.DynamicSelectTextField
import ru.yasdev.queryquill.components.SegmentedButtonSingleSelect
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel

@Composable
fun HttpRequestScreenHeader(
    type: HttpType,
    url: String,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
    headerState: MutableState<HttpRequestHeaderState>
) {
    Row(
        Modifier.padding(15.dp)
    ) {
        DynamicSelectTextField(
            selectedValue = type, options = listOf(
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
            value = url,
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
            val options = listOf(
                HttpRequestHeaderState.BODY,
                HttpRequestHeaderState.AUTH,
                HttpRequestHeaderState.HEADER,
                HttpRequestHeaderState.QUERY
            )
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