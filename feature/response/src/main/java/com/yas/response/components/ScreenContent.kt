package com.yas.response.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.model.CodeEditorState
import com.yas.model.ContentType
import com.yas.model.ImmutableList
import com.yas.model.KeyValue
import com.yas.response.headers.HeadersScreen
import com.yas.response.model.SegmentedButtonState
import com.yas.response.preview.PreviewScreen
import com.yas.response.source.ResponseScreenSource
import com.yas.utils.contentTypeToLanguageType
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