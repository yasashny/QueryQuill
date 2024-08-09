package com.yas.response

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.model.ImmutableList
import com.yas.model.KeyValue

@Composable
internal fun ResponseScreenHeaders(headers: ImmutableList<KeyValue>) {

    LazyColumn {
        items(headers.list) { item ->
            SelectionContainer {
                Column {
                    HorizontalDivider()
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(IntrinsicSize.Min)
                    ) {
                        Row {
                            Text(
                                text = item.key,
                                Modifier
                                    .padding(15.dp)
                                    .weight(1f)
                            )
                            VerticalDivider()
                            Text(
                                text = item.value,
                                Modifier
                                    .padding(15.dp)
                                    .weight(1f)
                            )
                        }
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}