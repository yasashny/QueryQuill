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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.model.ThemeState

@Composable
fun SettingsDialog(onDismiss: () -> Unit) {

    AlertDialog(onDismissRequest = { onDismiss() }, title = {
        Text(
            text = stringResource(id = R.string.settings),
            style = MaterialTheme.typography.titleLarge
        )
    }, modifier = Modifier.padding(vertical = 30.dp), text = {

        val vm = koinViewModel<SettingsViewModel>()

        when (val settingsState = vm.settingsUiState.collectAsState().value) {
            SettingsUiState.Loading -> {
                Box(modifier = Modifier.height(300.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
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
                            vm.updateModel(UpdateSettings.UpdateTheme(newThemeState))
                        }
                        FeedbackSection()
                        HorizontalDivider()
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val context = LocalContext.current
                            TextButton(onClick = {
                                context.startActivity(
                                    Intent(
                                        context, OssLicensesMenuActivity::class.java
                                    )
                                )
                            }) {
                                Text(text = stringResource(R.string.licenses))
                            }
                            val uriHandler = LocalUriHandler.current
                            TextButton(onClick = {
                                uriHandler.openUri(PRIVATE_POLICY_URL)
                            }) {
                                Text(text = stringResource(R.string.private_policy))
                            }
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            TextButton(onClick = { }, enabled = false) {
                                AppVersionText()
                            }
                        }
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
private fun ThemeSection(currentThemeState: ThemeState, updateTheme: (ThemeState) -> Unit) {
    Text(
        text = stringResource(id = R.string.theme),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
    val radioOptions = listOf(ThemeState.SYSTEM, ThemeState.DARK, ThemeState.LIGHT)
    Column(Modifier.selectableGroup()) {
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
private fun FeedbackSection() {
    Text(
        text = stringResource(id = R.string.feedback),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)
    ) {
        val context = LocalContext.current

        SelectionContainer {
            TextButton(onClick = {
                val clipboardManager =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData: ClipData = ClipData.newPlainText("text", "support@queryquill.org")
                clipboardManager.setPrimaryClip(clipData)
            }, modifier = Modifier.padding(start = 5.dp)) {
                Text("support@queryquill.org")
            }
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
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
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

    Text(text = "Version: $versionName")
}

private const val PRIVATE_POLICY_URL =
    "https://fancy-wombat-5c5.notion.site/Private-Policy-113654c8fbee80478663cba5a60a3a62?pvs=74"