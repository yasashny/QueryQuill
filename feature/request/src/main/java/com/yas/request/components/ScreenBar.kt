package com.yas.request.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.HttpType
import com.yas.model.ImmutableList
import com.yas.request.R

@Composable
internal fun ScreenBar(
    getType: () -> HttpType,
    getUrl: () -> String,
    updateType: (HttpType) -> Unit,
    updateUrl: (String) -> Unit
) {
    val type = getType()
    val url = getUrl()
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
            updateType(httpType)
        }
        OutlinedTextField(
            value = url,
            onValueChange = { updateUrl(it) },
            label = @Composable { Text(text = stringResource(R.string.url)) },
            modifier = Modifier
                .weight(2f)
                .padding(start = 15.dp)
        )
    }
}