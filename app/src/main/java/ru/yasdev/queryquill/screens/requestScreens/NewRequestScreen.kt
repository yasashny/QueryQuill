package ru.yasdev.queryquill.screens.requestScreens

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.RequestEvent

@Composable
fun NewRequestScreen(viewModel: MainActivityViewModel){
    
    Button(onClick = { viewModel.onEvent(RequestEvent.AddRequest(AddRequestModel("dufgdufhds"))) }) {
        
    }
    
}