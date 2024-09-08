package org.queryquill.app.feature.transaction.navigationDrawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.queryquill.app.core.utils.vibration
import org.queryquill.app.feature.transaction.R
import org.queryquill.app.feature.transaction.TransactionEvent

@Composable
internal fun NavigationDrawer(
    transactions: TransactionsUiState,
    navigateToSettings: () -> Unit,
    onEvent: (TransactionEvent) -> Unit,
    addTransactionDialog: @Composable (() -> Unit) -> Unit,
    composable: @Composable (drawerState: DrawerState) -> Unit
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                val kulimparkBold = FontFamily(
                    Font(R.font.kulimpark_bold, FontWeight.Bold)
                )
                Text(
                    text = stringResource(id = R.string.queryquill),
                    Modifier.padding(start = 27.dp, top = 18.dp, bottom = 14.dp),
                    style = TextStyle(
                        fontFamily = kulimparkBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.sp
                    ),
                    color = MaterialTheme.colorScheme.secondary
                )

                val settingsScope = rememberCoroutineScope()
                NavigationDrawerItem(
                    label = { Text(text = stringResource(R.string.settings)) },
                    selected = false,
                    onClick = {
                        settingsScope.launch {
                            drawerState.close()
                            navigateToSettings()
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                var openAddTransactionDialog by remember {
                    mutableStateOf(false)
                }
                if (openAddTransactionDialog) {
                    addTransactionDialog {
                        openAddTransactionDialog = false
                    }
                }
                NavigationDrawerItem(
                    label = { Text(text = stringResource(R.string.add_request)) },
                    selected = when (transactions) {
                        TransactionsUiState.Loading -> false
                        is TransactionsUiState.Success -> transactions.currentId == null
                    },
                    onClick = {
                        scope.launch {
                            vibration(context = context)
                            drawerState.close()
                            openAddTransactionDialog = true
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                HorizontalDivider(Modifier.padding(horizontal = 30.dp, vertical = 16.dp))
                when (transactions) {
                    TransactionsUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is TransactionsUiState.Success -> {
                        LazyColumn {
                            items(transactions.list) { item ->
                                NavigationDrawerItem(
                                    label = { Text(item.label) },
                                    selected = transactions.currentId == item.id,
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                            onEvent(
                                                TransactionEvent.SetTransaction(
                                                    item.id
                                                )
                                            )
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.http_icon),
                                            contentDescription = null
                                        )
                                    },
                                    badge = {
                                        IconButton(onClick = {
                                            vibration(context)
                                            if (transactions.currentId == item.id) {
                                                onEvent(
                                                    TransactionEvent.SetTransaction(null)
                                                )
                                            }
                                            onEvent(
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
        },
        content = {
            composable(drawerState)
        })
}