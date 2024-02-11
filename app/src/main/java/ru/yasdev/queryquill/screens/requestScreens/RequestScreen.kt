package ru.yasdev.queryquill.screens.requestScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.HttpRequestScreen

@Composable
fun RequestScreen(modifier: Modifier, viewModel: MainActivityViewModel){
    viewModel.qqq()

    val request by viewModel.request.collectAsState(initial = null)
    Box(modifier = modifier){

        if (request == null){
            Text(text = "NULLLLLLLL")
        }
        else{
            HttpRequestScreen(viewModel = viewModel)
        }

    }
    



}