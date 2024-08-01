package com.yas.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.yas.common.ThemeState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChangeThemeAlertDialog(
    openDialog: MutableState<Boolean>, currentTheme: ThemeState, updateTheme: (ThemeState) -> Unit
) {
    if (openDialog.value) {
        BasicAlertDialog(onDismissRequest = {
            openDialog.value = false
        }) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                val radioOptions = listOf(ThemeState.SYSTEM, ThemeState.DARK, ThemeState.LIGHT)
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(currentTheme) }


                Column(Modifier.selectableGroup()) {
                    Text(
                        text = "Theme",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    )
                    radioOptions.forEach { themeState ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (themeState == selectedOption), onClick = {
                                        onOptionSelected(themeState)
                                        updateTheme(themeState)
                                        openDialog.value = false
                                    }, role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (themeState == selectedOption), onClick = null
                            )
                            Text(
                                text = themeState.title,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}