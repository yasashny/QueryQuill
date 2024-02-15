package ru.yasdev.queryquill.screens.requestScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.yasdev.domain.utils.RequestState
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.HttpRequestScreen

@Composable
fun RequestScreen(modifier: Modifier, viewModel: MainActivityViewModel){
    viewModel.qqq()

    val request by viewModel.requestState.collectAsState(initial = RequestState.Loading)
    val qqq = viewModel.requestModel.collectAsState().value.id.toString()
    Box(modifier = modifier){

        if (request == RequestState.Loading){
            Text(text = "Loadingggg")
        }
        else if (request == RequestState.Null){
            NewRequestScreen(mainViewModel = viewModel)
        }
        else if (request == RequestState.Request){
            Text(text = qqq)
        }
        else{
            Text(text = "error")
        }

    }
    



}