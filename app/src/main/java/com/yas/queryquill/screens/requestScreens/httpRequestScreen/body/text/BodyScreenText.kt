package com.yas.queryquill.screens.requestScreens.httpRequestScreen.body.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.domain.requestsDb.states.BodyState

@Composable
fun BodyScreenText(
    bodyState: BodyState.Text, updateRequest: (BodyState.Text) -> Unit
) {
    OutlinedTextField(
        value = bodyState.text,
        onValueChange = { text ->
            updateRequest(BodyState.Text(text))
        },
        label = @Composable { Text(text = "Json/XML") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
            .heightIn(min = 150.dp)
    )
}