package com.yas.request.alertDialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.request.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoadingAlertDialog(
    onDismiss: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = {
        onDismiss()
    }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        }

    }
}