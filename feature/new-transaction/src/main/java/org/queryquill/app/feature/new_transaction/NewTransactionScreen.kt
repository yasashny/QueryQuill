package org.queryquill.app.feature.new_transaction


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun NewTransactionScreen() {
    val imageVector = painterResource(id = R.drawable.logoaddrequest)
    var openAddTransactionDialog by remember {
        mutableStateOf(false)
    }
    if (openAddTransactionDialog) {
        AddTransactionDialog(onDismiss = { openAddTransactionDialog = false })
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(350.dp)
            )
            Button(onClick = { openAddTransactionDialog = true }) {
                Text(
                    text = stringResource(R.string.create_new_request),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }
            Spacer(modifier = Modifier.height(65.dp))
        }
    }
}