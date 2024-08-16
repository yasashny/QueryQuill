package com.yas.request.requestScreenHeader

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.HttpType
import com.yas.model.ImmutableList
import com.yas.model.UpdateRequestModel
import com.yas.request.R
import com.yas.request.components.DynamicSelectTextField
import com.yas.request.components.SegmentedButtonSingleSelect

@Composable
internal fun HttpRequestScreenHeader(
    type: HttpType,
    url: String,
    updateRequest: (UpdateRequestModel) -> Unit,
    headerState: MutableState<HttpRequestHeaderState>
) {
    Row(
        Modifier.padding(15.dp)
    ) {
        DynamicSelectTextField(
            selectedValue = type, options = ImmutableList(
                listOf(
                    HttpType.GET,
                    HttpType.POST,
                    HttpType.PUT,
                    HttpType.PATCH,
                    HttpType.OPTIONS,
                    HttpType.DELETE,
                    HttpType.HEAD
                )
            ), label = stringResource(id = R.string.type), modifier = Modifier.weight(1.2f)
        ) { httpType ->
            updateRequest(UpdateRequestModel.Type(httpType))
        }
        OutlinedTextField(
            value = url,
            onValueChange = { updateRequest(UpdateRequestModel.Url(it)) },
            label = @Composable { Text(text = stringResource(R.string.url)) },
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
            SegmentedButtonSingleSelect(headerState,
                ImmutableList(options),
                onClick = { headerState.value = it })
        }

    }
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outlineVariant)
    )
}