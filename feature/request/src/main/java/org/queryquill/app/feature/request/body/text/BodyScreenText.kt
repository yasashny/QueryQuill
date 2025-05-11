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

package org.queryquill.app.feature.request.body.text


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.components.ChipGroup

@Composable
internal fun BodyScreenText(
    bodyState: BodyState.Text,
    updateTextType: (TextType) -> Unit,
    navigateToEditor: (fileName: String, textType: TextType) -> Unit
) {
    Column {
        Row {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, top = 15.dp)
            ) {
                ChipGroup(
                    currentState = bodyState.textType, options = ImmutableList(
                        listOf(
                            TextType.JSON, TextType.XML, TextType.PLAIN, TextType.OTHER
                        )
                    )
                ) { newState ->
                    if (bodyState.textType != newState) {
                        updateTextType(newState)
                    }
                }
            }
        }

        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 15.dp)
                .clickable {
                    navigateToEditor(bodyState.textFileName, bodyState.textType)
                },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                Text(
                    text = stringResource(R.string.input_your_body_here),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }
    }
}


