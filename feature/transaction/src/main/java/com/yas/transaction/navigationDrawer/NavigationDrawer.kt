package com.yas.transaction.navigationDrawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.transaction.R
import com.yas.transaction.TransactionEvent
import com.yas.transaction.TransactionViewModel
import com.yas.utils.vibration
import kotlinx.coroutines.launch

@Composable
internal fun NavigationDrawer(
    viewModel: TransactionViewModel,
    navigateToSettings: () -> Unit,
    composable: @Composable (drawerState: DrawerState) -> Unit
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val transactions by viewModel.transactions.collectAsState()

    var gesturesState by remember {
        mutableStateOf(false)
    }
    if (drawerState.isOpen) {
        gesturesState = true
    }
    if (drawerState.isClosed) {
        gesturesState = false
    }
    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = gesturesState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val settingsScope = rememberCoroutineScope()
                    IconButton(onClick = {
                        settingsScope.launch {
                            navigateToSettings()
                            drawerState.close()
                        }
                    }, Modifier.padding(start = 8.dp)) {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                    }
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                vibration(context = context)
                                drawerState.close()
                                viewModel.onEvent(TransactionEvent.SetTransaction(null))
                            }
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .height(50.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                        Text(
                            text = stringResource(R.string.new_request),
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            modifier = Modifier.padding(start = 5.dp, end = 15.dp)
                        )
                    }
                }
                HorizontalDivider(Modifier.padding(vertical = 12.dp))
                when (val state = transactions) {
                    TransactionsUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is TransactionsUiState.Success -> {
                        when (state.list.size) {
                            0 -> {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column {
                                        TextButton(onClick = {
                                            scope.launch {
                                                drawerState.close()
                                                viewModel.onEvent(
                                                    TransactionEvent.SetTransaction(
                                                        null
                                                    )
                                                )
                                            }
                                        }) {
                                            Text(
                                                text = stringResource(R.string.create_new_request),
                                                fontSize = MaterialTheme.typography.titleMedium.fontSize
                                            )
                                        }
                                    }
                                }
                            }

                            else -> {
                                LazyColumn {
                                    items(state.list) { item ->
                                        NavigationDrawerItem(
                                            label = { Text(item.label) },
                                            selected = state.currentId == item.id,
                                            onClick = {
                                                scope.launch {
                                                    drawerState.close()
                                                    viewModel.onEvent(
                                                        TransactionEvent.SetTransaction(
                                                            item.id
                                                        )
                                                    )
                                                }
                                            },
                                            badge = {
                                                IconButton(onClick = {
                                                    if (state.currentId == item.id){
                                                        viewModel.onEvent(
                                                            TransactionEvent.SetTransaction(null)
                                                        )
                                                    }
                                                    viewModel.onEvent(
                                                        TransactionEvent.DeleteTransaction(item.id)
                                                    )
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Delete,
                                                        contentDescription = null
                                                    )
                                                }
                                            },
                                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        content = {
            composable(drawerState)
        })
}