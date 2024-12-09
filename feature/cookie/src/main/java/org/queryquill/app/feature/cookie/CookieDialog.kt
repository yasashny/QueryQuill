package org.queryquill.app.feature.cookie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.ui.KeyValueItem

@Composable
fun CookieDialog(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize().padding(150.dp).padding(150.dp),
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(onDismissRequest = { onDismiss() }, title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Cookie",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                FilledTonalIconButton(onClick = {}) {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                }
            }




        },
            properties = DialogProperties(usePlatformDefaultWidth = false), text = {
                Column {
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth()
                    ) {
                        val testList: List<KeyValue> = listOf(
                            KeyValue("ds", "sds"),

                        )

                        items(testList) {
                            KeyValueItem(
                                keyValue = it,
                                onTextChanged = {},
                                modifier = Modifier.padding(vertical = 15.dp),
                                cardColors = CardDefaults.outlinedCardColors()
                                    .copy(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh), deleteButtonEnabled = {true}
                            )
                        }

                    }

                }


//        val vm = koinViewModel<SettingsViewModel>()
//
//        when (val settingsState = vm.settingsUiState.collectAsState().value) {
//            SettingsUiState.Loading -> {
//                Box(modifier = Modifier.height(300.dp), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            }
//
//            is SettingsUiState.Success -> {
//                Box(
//                    modifier = Modifier.wrapContentHeight(), contentAlignment = Alignment.TopCenter
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .wrapContentWidth()
//                            .widthIn(max = 900.dp)
//                            .verticalScroll(rememberScrollState()),
//                    ) {
//                        HorizontalDivider()
//                        ThemeSection(currentThemeState = settingsState.settingsModel.themeState) { newThemeState ->
//                            vm.updateModel(UpdateSettings.UpdateTheme(newThemeState))
//                        }
//                        FeedbackSection()
//                        HorizontalDivider()
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            modifier = Modifier.fillMaxSize()
//                        ) {
//                            val context = LocalContext.current
//                            TextButton(onClick = {
//                                context.startActivity(
//                                    Intent(
//                                        context, OssLicensesMenuActivity::class.java
//                                    )
//                                )
//                            }) {
//                                Text(text = stringResource(R.string.licenses))
//                            }
//                            val uriHandler = LocalUriHandler.current
//                            TextButton(onClick = {
//                                uriHandler.openUri(PRIVATE_POLICY_URL)
//                            }) {
//                                Text(text = stringResource(R.string.private_policy))
//                            }
//                        }
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            modifier = Modifier.fillMaxSize()
//                        ) {
//                            TextButton(onClick = { }, enabled = false) {
//                                AppVersionText()
//                            }
//                        }
//                    }
//                }
//            }
//        }
            }, confirmButton = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "*cookies are automatically sent with relevant requests",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = "OK")
                    }
                }
            })
    }


}