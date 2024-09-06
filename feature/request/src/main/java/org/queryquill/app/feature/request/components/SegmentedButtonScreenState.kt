package org.queryquill.app.feature.request.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.feature.request.ScreenState

@Composable
internal fun SegmentedButtonScreenState(
    screenState: ScreenState, updateScreenState: (ScreenState) -> Unit
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
    ) {
        val options = listOf(
            ScreenState.BODY, ScreenState.AUTH, ScreenState.HEADER, ScreenState.QUERY
        )
        SegmentedButton(screenState, ImmutableList(options), onClick = { updateScreenState(it) })
    }
}

