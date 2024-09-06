package org.queryquill.app.feature.transaction

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.queryquill.app.core.model.Transaction
import org.queryquill.app.core.ui.QueryQuillTopBar
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState

@Composable
internal fun TransactionTopBar(
    transactions: TransactionsUiState,
    drawerState: DrawerState,
    updateTransaction: (transaction: Transaction) -> Unit
) {


    QueryQuillTopBar(title = {
        when (transactions) {
            TransactionsUiState.Loading -> {
                Text(
                    stringResource(R.string.queryquill),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            is TransactionsUiState.Success -> {
                Text(
                    transactions.list.find { it.id == transactions.currentId }?.label
                        ?: stringResource(R.string.queryquill),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }, navigationIcon = {
        val scope = rememberCoroutineScope()

        IconButton(onClick = { scope.launch(Dispatchers.IO) { drawerState.open() } }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
        }
    }, actions = {
        when (transactions) {
            TransactionsUiState.Loading -> {}
            is TransactionsUiState.Success -> {
                when (val id = transactions.currentId) {
                    null -> {}
                    else -> {
                        var openChangeLabelDialog by remember {
                            mutableStateOf(false)
                        }
                        if (openChangeLabelDialog) {
                            ChangeLabelAlertDialog(onDismiss = { openChangeLabelDialog = false },
                                onConfirm = { newLabel: String ->
                                    updateTransaction(
                                        Transaction(
                                            id = id, label = newLabel
                                        )
                                    )
                                    openChangeLabelDialog = false
                                })
                        }
                        IconButton(onClick = {
                            openChangeLabelDialog = true
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