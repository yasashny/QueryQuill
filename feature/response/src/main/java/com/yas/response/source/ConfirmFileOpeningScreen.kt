package com.yas.response.source

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ConfirmFileOpeningScreen(onConfirm: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = "A file larger than 5 MB is hidden for performance reasons",
                Modifier.padding(15.dp)
            )
            Button(
                onClick = { onConfirm() }, Modifier.padding(15.dp)
            ) {
                Text(text = "Show anyway")
            }
        }
    }
}