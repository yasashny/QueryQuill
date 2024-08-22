package com.yas.response

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yas.model.ImmutableList
import com.yas.response.components.ResponseScreenBar
import com.yas.response.components.SegmentedButtonResponse
import com.yas.response.preview.ResponseScreenPreview
import com.yas.response.source.ResponseScreenSource
import com.yas.utils.contentTypeToLanguageType
import org.koin.androidx.compose.koinViewModel
import java.io.File


@Composable
fun ResponseScreen(
    modifier: Modifier
) {
    Column(modifier = modifier) {
        val vm = koinViewModel<ResponseViewModel>()
        val responseModel = vm.responseModel.collectAsState().value
        val responseFile = remember(responseModel) {
            File(vm.getFileUriByName(responseModel.fileName))
        }
        Box(
            modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.White)
        ) {
            Text(text = "dasdad")
        }

        ResponseScreenBar(
            status = responseModel.status,
            time = responseModel.time,
            contentLength = responseModel.contentLength,
            file = responseFile
        )

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
                ResponseScreenPreview(
                    contentType = responseModel.contentType,
                    file = responseFile
                )
            }

            ResponseSegmentedButtonState.SOURCE -> {
                val languageType = contentTypeToLanguageType(responseModel.contentType)
                ResponseScreenSource(
                    languageType, responseFile
                )
            }

            ResponseSegmentedButtonState.HEADERS -> {
                ResponseScreenHeaders(responseModel.headers)
            }
        }
    }
}