package com.yas.queryquill.screens.settingsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.domain.settings.ThemeState

@Composable
fun SettingsScreen(theme: ThemeState, updateSettings: (UpdateSettings) -> Unit) {

    val openDialog = remember {
        mutableStateOf(false)
    }
    if (openDialog.value) {
        ChangeThemeAlertDialog(openDialog = openDialog, currentTheme = theme) { newThemeState ->
            updateSettings(UpdateSettings.UpdateTheme(newThemeState))

        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .widthIn(max = 900.dp)
        ) {
            ListItem(
                headlineContent = { Text("Theme") },
                supportingContent = {
                    Text(text = theme.title)
                },
                modifier = Modifier
                    .clickable {
                        openDialog.value = true
                    }
            )
        }
    }


}