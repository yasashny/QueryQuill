package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.auth

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.yasdev.domain.requestsDb.models.AuthState

@Composable
fun AuthChipGroup(authState: AuthState, onClick: (AuthState) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            val options = listOf(
                AuthState.NoAuth, AuthState.Basic.default()
            )
            options.forEachIndexed { index, chipState ->
                InputChip(modifier = if (index == 0) {
                    Modifier.padding(start = 29.dp, end = 4.dp)
                } else {
                    Modifier.padding(horizontal = 4.dp)
                },
                    onClick = { onClick(chipState) },
                    label = { Text(chipState.name) },
                    selected = authState::class == chipState::class,
                    leadingIcon = {
                        if (authState::class == chipState::class) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Localized Description",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    })
            }
        }
    }
}