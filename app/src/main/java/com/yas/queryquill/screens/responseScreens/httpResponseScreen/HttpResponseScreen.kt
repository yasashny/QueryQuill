package com.yas.queryquill.screens.responseScreens.httpResponseScreen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.yas.domain.sendRequest.ResponseModel
import com.yas.queryquill.components.codeEditor.mimeTypeToLanguageType
import com.yas.queryquill.screens.responseScreens.ResponseScreenSource
import com.yas.queryquill.screens.responseScreens.httpResponseScreen.preview.ResponseScreenPreview
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HttpResponseScreen(
    modifier: Modifier, responseModelFlow: StateFlow<ResponseModel>
) {
    Box(modifier = modifier) {
        val responseModel by responseModelFlow.collectAsState()
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
                        text = "${responseModel.time} ms",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                    VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = "${responseModel.contentLength} bytes",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            var responseState by remember {
                mutableStateOf(ResponseState.PREVIEW)
            }
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                ) {
                    SegmentedButtonResponse(
                        currentState = responseState, options = listOf(
                            ResponseState.PREVIEW, ResponseState.SOURCE, ResponseState.HEADERS
                        )
                    ) { newState ->
                        if (newState != responseState) {
                            responseState = newState
                        }
                    }
                }

            }
            when (responseState) {
                ResponseState.PREVIEW -> {
                    ResponseScreenPreview(
                        body = responseModel.body,
                        mimeType = responseModel.contentType,
                        contentSubtype = responseModel.contentSubtype
                    )
                }

                ResponseState.SOURCE -> {
                    val languageType =
                        mimeTypeToLanguageType("${responseModel.contentType}/${responseModel.contentSubtype}")
                    ResponseScreenSource(
                        responseModel.body.decodeToString(), languageType
                    )
                }

                ResponseState.HEADERS -> {
                    ResponseScreenHeaders(responseModel.headers)
                }
            }
        }
    }
}
