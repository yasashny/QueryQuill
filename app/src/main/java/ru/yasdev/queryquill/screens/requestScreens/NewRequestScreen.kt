package ru.yasdev.queryquill.screens.requestScreens

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.RequestEvent

@Composable
fun NewRequestScreen(mainViewModel: MainActivityViewModel){
    val viewModel = koinViewModel<NewRequestScreenViewModel>()

    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){


        val label by viewModel.label.collectAsState()
        OutlinedCard(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        ) {
            Column {
                Text(text = "New Request",
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp), fontSize = MaterialTheme.typography.headlineSmall.fontSize, textAlign = TextAlign.Center)
                OutlinedTextField(value = label, onValueChange = {l -> viewModel.changeNewRequestLabel(l)},
                    Modifier
                        .align(
                            Alignment.CenterHorizontally
                        )
                        .padding(top = 15.dp), label = @Composable{ Text(text = "label")})
                OutlinedButton(onClick = { viewModel.addRequest(mainViewModel) }, modifier = Modifier
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .padding(bottom = 15.dp, top = 15.dp)) {
                    Text(text = "Add request")
            }


            }
        }

    }



    

    
}