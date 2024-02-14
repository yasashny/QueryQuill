package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import android.annotation.SuppressLint
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
import ru.yasdev.queryquill.activity.MainActivityViewModel
import java.util.concurrent.Flow

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HttpRequestScreen(viewModel: MainActivityViewModel) {


        Row{
            val qqq = viewModel.requestModel.collectAsState().value.id.toString()
            Text(text = qqq)
            Column {
                val a = viewModel.counter.collectAsState()
                Text(text = "firstScreen")
                Text(text = a.value)
                TextField(value = a.value, onValueChange = {newText -> viewModel.incrementCounter(newText)}, modifier = Modifier.background(
                    Color.Transparent))
                Button(onClick = {   }) {

                }

            }
        }




}