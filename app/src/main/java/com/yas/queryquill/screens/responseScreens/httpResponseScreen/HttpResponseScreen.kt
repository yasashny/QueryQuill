package com.yas.queryquill.screens.responseScreens.httpResponseScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.domain.sendRequest.ResponseModel
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
            Row {
                OutlinedCard(
                    Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(7.dp),
                            text = "Status: ${responseModel.status}",
                        )
                    }
                }
                OutlinedCard(
                    Modifier.padding(end = 15.dp, top = 15.dp, bottom = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(7.dp),
                            text = "Time: ${responseModel.time} ms",
                        )
                    }
                }
                OutlinedCard(
                    Modifier.padding(end = 15.dp, top = 15.dp, bottom = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(7.dp),
                            text = "${responseModel.contentLength} byte",
                        )
                    }
                }
            }

            var responseState by remember {
                mutableStateOf(ResponseState.PREVIEW)
            }
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp)
                ) {
                    SegmentedButtonResponse(
                        currentState = responseState,
                        options = listOf(ResponseState.PREVIEW, ResponseState.SOURCE)
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
                        contentType = responseModel.contentType,
                        contentSubtype = responseModel.contentSubtype
                    )
                }

                ResponseState.SOURCE -> {
                    ResponseScreenSource(responseModel.body.decodeToString())
                }
            }
        }
    }
}
