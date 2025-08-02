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

package org.queryquill.app.feature.settings

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.SettingsModel
import org.queryquill.app.core.model.ThemeState
import org.queryquill.app.feature.settings.util.TestTags

@Composable
fun SettingsDialog(onDismiss: () -> Unit) {
    val viewModel = koinViewModel<SettingsViewModel>()
    val settingsState = viewModel.settingsUiState.collectAsStateWithLifecycle().value
    SettingsDialog(onDismiss, settingsState, viewModel::updateModel)
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun SettingsDialog(
    onDismiss: () -> Unit, settingsState: SettingsUiState, updateModel: (UpdateSettings) -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss() }, title = {
        Text(
            text = stringResource(id = R.string.settings),
            style = MaterialTheme.typography.titleLarge
        )
    }, modifier = Modifier.padding(vertical = 30.dp), text = {

        when (settingsState) {
            SettingsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator(Modifier.testTag(TestTags.CIRCULAR_PROGRESS_INDICATOR))
                }
            }

            is SettingsUiState.Success -> {
                Box(
                    modifier = Modifier.wrapContentHeight(), contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .widthIn(max = 900.dp)
                            .verticalScroll(rememberScrollState()),
                    ) {
                        HorizontalDivider()
                        ThemeSection(currentThemeState = settingsState.settingsModel.themeState) { newThemeState ->
                            updateModel(UpdateSettings.UpdateTheme(newThemeState))
                        }
                        HorizontalDivider(Modifier.padding(top = Dimens.small))
                        BottomButtonsSection()
                        FeedbackSection()
                        VersionSection()
                    }
                }
            }
        }
    }, confirmButton = {
        TextButton(onClick = { onDismiss() }) {
            Text(text = stringResource(R.string.ok))
        }
    })
}

@Composable
private fun BottomButtonsSection() {
    Row(
        horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
    ) {
        val context = LocalContext.current
        TextButton(onClick = {
            context.startActivity(
                Intent(
                    context, OssLicensesMenuActivity::class.java
                )
            )
        }, modifier = Modifier.testTag(TestTags.SOURCE_CODE_BUTTON)) {
            Text(text = stringResource(R.string.licenses))
        }
        val uriHandler = LocalUriHandler.current
        TextButton(onClick = {
            uriHandler.openUri(PRIVATE_POLICY_URL)
        }, modifier = Modifier.testTag(TestTags.PRIVACY_POLICY_BUTTON)) {
            Text(text = stringResource(R.string.privacy_policy))
        }
    }
}

@Composable
private fun FeedbackSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current

        SelectionContainer {
            TextButton(onClick = {
                val clipboardManager =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData: ClipData = ClipData.newPlainText("text", CONTACT_EMAIL)
                clipboardManager.setPrimaryClip(clipData)
            }, modifier = Modifier.testTag(TestTags.FEEDBACK_BUTTON)) {
                Text(CONTACT_EMAIL)
            }
        }
    }
}

@Composable
private fun VersionSection() {
    Row(
        horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()
    ) {
        TextButton(
            onClick = { }, enabled = false, modifier = Modifier.testTag(TestTags.VERSION_BUTTON)
        ) {
            AppVersionText()
        }
    }
}

@Composable
private fun ThemeSection(currentThemeState: ThemeState, updateTheme: (ThemeState) -> Unit) {
    Text(
        text = stringResource(id = R.string.theme),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = Dimens.medium, bottom = Dimens.small),
    )
    val radioOptions = listOf(ThemeState.SYSTEM, ThemeState.DARK, ThemeState.LIGHT)
    Column(
        Modifier
            .selectableGroup()
            .testTag(TestTags.THEME_SECTION)
    ) {
        radioOptions.forEach { themeState ->
            ChooseRow(
                text = themeState.title,
                selected = currentThemeState == themeState,
                onClick = { updateTheme(themeState) },
            )
        }
    }
}

@Composable
private fun ChooseRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected, onClick = null
        )
        Spacer(Modifier.width(Dimens.small))
        Text(text)
    }
}

@Composable
private fun AppVersionText() {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val packageInfo = try {
        packageManager.getPackageInfo(context.packageName, 0)
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }

    val versionName = packageInfo?.versionName ?: "Unknown"

    Text(text = "Version: $versionName", modifier = Modifier.testTag(TestTags.VERSION_TEXT))
}

@Preview
@Composable
private fun PreviewSettingsDialog() {
    QueryQuillTheme {
        SettingsDialog(
            settingsState = SettingsUiState.Success(SettingsModel(ThemeState.LIGHT)),
            onDismiss = {},
            updateModel = {})
    }
}

@Preview
@Composable
private fun PreviewSettingsDialogLoading() {
    QueryQuillTheme {
        SettingsDialog(settingsState = SettingsUiState.Loading, onDismiss = {}, updateModel = {})
    }
}

private const val PRIVATE_POLICY_URL =
    "https://pewter-brow-ce5.notion.site/Private-Policy-127ace70ed4a8035abedc09a2751c51e"

internal const val CONTACT_EMAIL = "support@queryquill.org"