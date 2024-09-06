package org.queryquill.app.feature.request.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import org.queryquill.app.core.utils.vibration
import org.queryquill.app.feature.request.R

@Composable
internal fun SendRequestButton(onClick: () -> Unit) {
    val context = LocalContext.current
    ExtendedFloatingActionButton(onClick = {
        vibration(context)
        onClick()
    }, icon = {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Send, contentDescription = null
        )
    }, text = { Text(text = stringResource(R.string.send_request)) })
}