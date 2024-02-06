package ru.yasdev.queryquill.screens.httpResponseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HttpResponseScreen(modifier: Modifier) {

    Box(modifier = modifier) {
        LazyColumn {
            items(60) { index ->
                Text(text = "qqq $index")
            }
        }
    }

}