package com.yas.queryquill.screens.requestScreens.newRequestScreen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yas.domain.requestsDb.models.AddRequestModel

@Composable
fun NewRequestScreen(addRequest: (model: AddRequestModel) -> Unit) {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var label by remember {
            mutableStateOf("My Request")
        }
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Column {
                Text(
                    text = "New Request",
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(value = label,
                    onValueChange = { newLabel -> label = newLabel },
                    Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(top = 15.dp),
                    label = { Text(text = "Label") })
                OutlinedButton(
                    onClick = { addRequest(AddRequestModel(label = label)) }, modifier = Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(bottom = 15.dp, top = 15.dp),
                    enabled = label.isNotEmpty()
                ) {
                    Text(text = "Add request")
                }
            }
        }
    }
}