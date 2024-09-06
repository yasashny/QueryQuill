package org.queryquill.app.feature.response.source

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.queryquill.app.feature.response.R

@Composable
internal fun FileCannotBeOpenedScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.files_larger_than_100_megabytes_cannot_be_previewed_due_to_performance_reasons_you_can_download_the_file),
            modifier = Modifier.padding(15.dp),
            textAlign = TextAlign.Center
        )
    }
}