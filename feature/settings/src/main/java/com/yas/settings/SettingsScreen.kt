package com.yas.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.feature_settings.R
import com.yas.ui.QueryQuillTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(navigateUp: () -> Unit) {

    val vm = koinViewModel<SettingsViewModel>()

    Scaffold(topBar = {
        QueryQuillTopBar(title = { Text(text = stringResource(R.string.settings)) },
            navigationIcon = {
                IconButton(onClick = { navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null
                    )
                }
            })
    }) {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            when (val settingsState = vm.settingsState.collectAsState().value) {
                SettingsUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is SettingsUiState.Success -> {
                    val openDialog = remember {
                        mutableStateOf(false)
                    }
                    if (openDialog.value) {
                        ChangeThemeAlertDialog(
                            openDialog = openDialog,
                            currentTheme = settingsState.settingsModel.themeState
                        ) { newThemeState ->
                            vm.updateModel(UpdateSettings.UpdateTheme(newThemeState))

                        }
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .widthIn(max = 900.dp)
                        ) {
                            ListItem(headlineContent = { Text(stringResource(id = R.string.theme)) },
                                supportingContent = {
                                    Text(text = settingsState.settingsModel.themeState.title)
                                },
                                modifier = Modifier.clickable {
                                    openDialog.value = true
                                })
                        }
                    }
                }
            }
        }
    }
}