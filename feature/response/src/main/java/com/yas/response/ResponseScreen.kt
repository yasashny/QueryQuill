package com.yas.response

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.ImmutableList
import com.yas.model.ResponseModel
import com.yas.response.components.SegmentedButtonResponse
import com.yas.utils.mimeTypeToLanguageType
import java.net.URI

@Composable
fun ResponseScreen(
    modifier: Modifier, responseModel: ResponseModel, getTextFileUri: (textFileName: String) -> URI
) {
    Box(modifier = modifier) {

        Column(Modifier.fillMaxSize()) {
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .height(IntrinsicSize.Min)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = responseModel.status,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                    )
                    VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = stringResource(R.string.ms, responseModel.time),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                    VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = stringResource(R.string.bytes, responseModel.contentLength),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            var responseSegmentedButtonState by remember {
                mutableStateOf(ResponseSegmentedButtonState.PREVIEW)
            }
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                ) {
                    SegmentedButtonResponse(
                        currentState = responseSegmentedButtonState, options = ImmutableList(
                            listOf(
                                ResponseSegmentedButtonState.PREVIEW,
                                ResponseSegmentedButtonState.SOURCE,
                                ResponseSegmentedButtonState.HEADERS
                            )
                        )
                    ) { newState ->
                        if (newState != responseSegmentedButtonState) {
                            responseSegmentedButtonState = newState
                        }
                    }
                }
            }
            when (responseSegmentedButtonState) {
                ResponseSegmentedButtonState.PREVIEW -> {
//                    ResponseScreenPreview(
//                        body = responseModel.fileName,
//                        mimeType = responseModel.contentType,
//                        contentSubtype = responseModel.contentSubtype
//                    )
                }

                ResponseSegmentedButtonState.SOURCE -> {
                    val languageType =
                        mimeTypeToLanguageType("${responseModel.contentType}/${responseModel.contentSubtype}")
                    ResponseScreenSource(
                        responseModel.fileName, languageType,
                        getTextFileUri
                    )
                }

                ResponseSegmentedButtonState.HEADERS -> {
                    ResponseScreenHeaders(responseModel.headers)
                }
            }
        }
    }
}
