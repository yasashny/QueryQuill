package ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HttpResponseScreen(modifier: Modifier, httpResponseScreenViewModel: HttpResponseScreenViewModel) {

    Box(modifier = modifier) {
        Row{
            LazyColumn {
                items(60) { index ->
                    Text(text = "qqq $index")
                }
            }
            Column {
                val a = httpResponseScreenViewModel.counter.collectAsState()
                Text(text = "firstScreen")
                Text(text = a.value)
                TextField(value = a.value, onValueChange = {newText -> httpResponseScreenViewModel.incrementCounter(newText)}, modifier = Modifier.background(
                    Color.Transparent))
                Button(onClick = {  }) {

                }

            }
        }

    }

}