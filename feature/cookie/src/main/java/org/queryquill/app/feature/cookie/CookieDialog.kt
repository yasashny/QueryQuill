package org.queryquill.app.feature.cookie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.ui.KeyValueItem

@Composable
fun CookieDialog(onDismiss: () -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val dialogMaxHeight = screenHeight * 0.8f

    AlertDialog(onDismissRequest = { onDismiss() }, title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Cookie",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            FilledTonalIconButton(onClick = {}) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
            }
        }


    }, properties = DialogProperties(
        usePlatformDefaultWidth = false
    ), modifier = Modifier
        .padding(horizontal = 16.dp).heightIn(max = dialogMaxHeight), text = {
        Column {
            LazyColumn(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                val testList: List<KeyValue> = listOf(
                    KeyValue("ds", "sds"),
                    KeyValue("ds", "sds"),
                    KeyValue("ds", "sds"),
                    KeyValue("ds", "sds"),

                    )

                items(testList) {
                    KeyValueItem(keyValue = it,
                        onTextChanged = {},
                        modifier = Modifier.padding(vertical = 15.dp),
                        cardColors = CardDefaults.outlinedCardColors()
                            .copy(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                        deleteButtonEnabled = { true })
                }

            }

        }

    }, confirmButton = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "*cookies are automatically sent with relevant requests",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = { onDismiss() }) {
                Text(text = "OK")
            }
        }
    })
}


