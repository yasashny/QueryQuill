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

package org.queryquill.app.feature.request.body


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.components.ChipGroup

@Composable
internal fun BodyScreenText(
    modifier: Modifier = Modifier,
    bodyState: BodyState.Text,
    updateTextType: (TextType) -> Unit,
    navigateToEditor: (fileName: String, textType: TextType) -> Unit
) {
    val textTypeOptions = remember { TextType.entries }

    Column(modifier = modifier) {
        HorizontalDivider()
        ChipGroup(
            current = bodyState.textType, options = textTypeOptions, onSelect = { newState ->
                if (bodyState.textType != newState) updateTextType(newState)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

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

@Preview(name = "BodyScreenText", showBackground = true)
@Composable
private fun PreviewBodyScreenText() {
    QueryQuillTheme(dynamicColor = false) {
        BodyScreenText(
            bodyState = BodyState.Text(textFileName = "body.txt", textType = TextType.PLAIN),
            updateTextType = {},
            navigateToEditor = { _, _ -> })
    }
}

