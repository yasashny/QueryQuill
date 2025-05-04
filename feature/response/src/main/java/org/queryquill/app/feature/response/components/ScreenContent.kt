package org.queryquill.app.feature.response.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.feature.response.headers.HeadersScreen
import org.queryquill.app.feature.response.model.SegmentedButtonState
import org.queryquill.app.feature.response.preview.PreviewScreen
import org.queryquill.app.feature.response.source.ResponseScreenSource
import org.queryquill.app.feature.response.utils.contentTypeToLanguageType
import java.io.File

@Composable
internal fun ScreenContent(
    contentType: ContentType,
    file: File,
    headers: ImmutableList<KeyValue>,
    segmentedButtonState: SegmentedButtonState,
    updateSegmentedButtonState: (SegmentedButtonState) -> Unit,
    codeEditorState: CodeEditorState
) {
    Row {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        ) {
            SegmentedButton(
                currentState = segmentedButtonState, options = ImmutableList(
                    listOf(
                        SegmentedButtonState.PREVIEW,
                        SegmentedButtonState.SOURCE,
                        SegmentedButtonState.HEADERS
                    )
                )
            ) { newState ->
                if (newState != segmentedButtonState) {
                    updateSegmentedButtonState(newState)
                }
            }
        }
    }

    when (segmentedButtonState) {
        SegmentedButtonState.PREVIEW -> {
            PreviewScreen(
                contentType = contentType, file = file, codeEditorState
            )
        }

        SegmentedButtonState.SOURCE -> {
            val languageType = contentTypeToLanguageType(contentType)
            ResponseScreenSource(
                languageType, file, codeEditorState
            )
        }

        SegmentedButtonState.HEADERS -> {
            HeadersScreen(headers)
        }
    }
}