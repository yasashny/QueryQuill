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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yas.response.R

@Composable
internal fun ConfirmFileOpeningScreen(onConfirm: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.a_file_larger_than_5_mb_is_hidden_for_performance_reasons),
                Modifier.padding(15.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { onConfirm() }, modifier = Modifier.padding(top = 15.dp)
            ) {
                Text(text = stringResource(R.string.show_anyway))
            }
        }
    }
}