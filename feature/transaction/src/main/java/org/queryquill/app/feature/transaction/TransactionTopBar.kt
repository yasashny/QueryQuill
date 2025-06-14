/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.feature.transaction

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.queryquill.app.core.model.Transaction
import org.queryquill.app.core.ui.QueryQuillTopBar
import org.queryquill.app.feature.transaction.navigationDrawer.TransactionsUiState
import org.queryquill.app.feature.transaction.util.TestTags

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
                val label = transactions.list.find { it.id == transactions.currentId }?.label
                if (label != null) {
                    Text(
                        label, maxLines = 1, overflow = TextOverflow.Ellipsis
                    )
                } else {
                    val kulimparkBold = FontFamily(
                        Font(R.font.kulimpark_bold, FontWeight.Bold)
                    )
                    Text(
                        text = stringResource(id = R.string.queryquill), style = TextStyle(
                            fontFamily = kulimparkBold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            lineHeight = 24.sp,
                            letterSpacing = 0.sp
                        ), color = MaterialTheme.colorScheme.secondary
                    )
                }


            }
        }
    }, navigationIcon = {
        val scope = rememberCoroutineScope()

        IconButton(
            onClick = { scope.launch(Dispatchers.IO) { drawerState.open() } },
            modifier = Modifier.testTag(TestTags.TransactionTopBar.MENU_BUTTON)
        ) {
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
                            ChangeLabelAlertDialog(
                                onDismiss = { openChangeLabelDialog = false },
                                onConfirm = { newLabel: String ->
                                    updateTransaction(
                                        Transaction(
                                            id = id, label = newLabel
                                        )
                                    )
                                    openChangeLabelDialog = false
                                })
                        }
                        IconButton(
                            onClick = {
                                openChangeLabelDialog = true
                            },
                            modifier = Modifier.testTag(TestTags.TransactionTopBar.CHANGE_LABEL_BUTTON)
                        ) {
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

@Preview
@Composable
private fun PreviewTransactionTopBar() {
    TransactionTopBar(
        TransactionsUiState.Success(listOf(), null), DrawerState(DrawerValue.Closed)
    ) {}
}