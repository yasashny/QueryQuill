package ru.yasdev.queryquill.screens.httpRequestScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

@Composable
fun HttpRequestScreen(modifier: Modifier, httpRequestScreenViewModel: HttpRequestScreenViewModel) {

    Box(modifier = modifier) {
        Row{
            LazyColumn {
                items(60) { index ->
                    Text(text = "qqq $index")
                }
            }
            Column {
                val a = httpRequestScreenViewModel.counter.collectAsState()
                Text(text = "firstScreen")
                Text(text = a.value)
                TextField(value = a.value, onValueChange = {newText -> httpRequestScreenViewModel.incrementCounter(newText)}, modifier = Modifier.background(
                    Color.Transparent))
                Button(onClick = {   }) {

                }

            }
        }

    }


}