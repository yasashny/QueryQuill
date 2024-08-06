package com.yas.queryquill.screens.requestScreens.httpRequestScreen.body.text


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
import androidx.compose.ui.unit.dp
import com.yas.model.BodyState
import com.yas.model.TextType
import com.yas.queryquill.components.ChipGroup

@Composable
fun BodyScreenText(
    bodyState: BodyState.Text, updateRequest: (BodyState.Text) -> Unit, navigateToEditor: () -> Unit
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
                    currentState = bodyState.textType, options = listOf(
                        TextType.JSON, TextType.XML, TextType.PLAIN, TextType.OTHER
                    )
                ) { newState ->
                    if (bodyState.textType::class != newState::class) {
                        updateRequest(BodyState.Text(bodyState.text, newState as TextType))
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
                    navigateToEditor()
                },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                Text(
                    text = "Input your body here...",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    modifier = Modifier.padding(start = 15.dp)
                )
            }
        }
    }
}


