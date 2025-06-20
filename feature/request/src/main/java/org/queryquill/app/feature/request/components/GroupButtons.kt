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

package org.queryquill.app.feature.request.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.feature.request.ScreenState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun GroupButtons(
    screenState: ScreenState,
    updateScreenState: (ScreenState) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = remember { ScreenState.entries }
    Row(
        modifier
            .padding(horizontal = Dimens.small)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
    ) {
        options.forEachIndexed { index, element ->
            ToggleButton(
                checked = screenState == element,
                onCheckedChange = { updateScreenState(element) },
                modifier = Modifier
                    .weight(1f)
                    .semantics { role = Role.RadioButton },
                shapes = when (index) {
                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                }
            ) {
                Text(element.title)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGroupButtons() {
    QueryQuillTheme {
        GroupButtons(
            screenState = ScreenState.BODY, updateScreenState = {})
    }
}

