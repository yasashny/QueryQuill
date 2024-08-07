package com.yas.transaction

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.yas.model.Transaction
import com.yas.transaction.navigationDrawer.TransactionsUiState
import com.yas.ui.QueryQuillTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TransactionTopBar(vm: TransactionViewModel, drawerState: DrawerState) {

    val transactions by vm.transactions.collectAsState()

    QueryQuillTopBar(title = {
        when (val state = transactions) {
            TransactionsUiState.Loading -> {
                Text(
                    "QueryQuill", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }

            is TransactionsUiState.Success -> {
                Text(
                    state.list.find { it.id == state.currentId }?.label ?: "QueryQuill",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    },
        navigationIcon = {
            val scope = rememberCoroutineScope()

            IconButton(onClick = { scope.launch(Dispatchers.IO) { drawerState.open() } }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            when(val state = transactions){
                TransactionsUiState.Loading -> {}
                is TransactionsUiState.Success -> {
                    when(val id = state.currentId){
                        null -> {}
                        else -> {
                            val openDialog = remember {
                                mutableStateOf(false)
                            }
                            val flag = remember {
                                mutableStateOf<String?>(null)
                            }
                            if (flag.value != null) {
                                vm.onEvent(TransactionEvent.UpdateTransaction(Transaction(id = id, label = flag.value as String)))
                                flag.value = null
                            }
                            if (openDialog.value) {
                                ChangeLabelAlertDialog(openDialog, flag)
                            }
                            IconButton(onClick = {
                                openDialog.value = true
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit, contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        })

}