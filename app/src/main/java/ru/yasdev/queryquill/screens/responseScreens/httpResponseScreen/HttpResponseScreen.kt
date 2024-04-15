package ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.yasdev.domain.sendRequest.ResponseModel

@Composable
fun HttpResponseScreen(
    modifier: Modifier, responseState: ResponseModel
) {
    Box(modifier = modifier){
        Column(Modifier.fillMaxSize()) {
            Text(text = responseState.status)
            Text(text = responseState.contentLength)
            Text(text = responseState.time)
            Text(text = responseState.body)
        }
    }


}