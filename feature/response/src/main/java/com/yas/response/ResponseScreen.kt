package com.yas.response

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.ImmutableList
import com.yas.response.components.SegmentedButtonResponse
import com.yas.response.preview.ResponseScreenPreview
import com.yas.response.preview.saveFile
import com.yas.utils.contentTypeToLanguageType
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun ResponseScreen(
    modifier: Modifier
) {
    Box(modifier = modifier) {
        val vm = koinViewModel<ResponseViewModel>()
        val responseModel = vm.responseModel.collectAsState().value
        val file = File(vm.getFileUriByName(responseModel.fileName))
        val saveFile = saveFile(file)

        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, top = 23.dp, end = 15.dp, bottom = 15.dp)
                    .height(56.dp)
                    .border(
                        1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(4.dp)
                    )

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
                    VerticalDivider(color = MaterialTheme.colorScheme.outline)
                    Text(
                        text = stringResource(R.string.ms, responseModel.time),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                    VerticalDivider(color = MaterialTheme.colorScheme.outline)
                    Text(
                        text = stringResource(R.string.bytes, responseModel.contentLength),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(10.dp)
                    )
                    VerticalDivider(color = MaterialTheme.colorScheme.outline)
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        IconButton(
                            onClick = { saveFile.launch(file.name) },
                            enabled = file.name != "default.txt"
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.download),
                                contentDescription = null,
                                Modifier.size(30.dp)
                            )
                        }
                    }
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
                    ResponseScreenPreview(
                        fileName = responseModel.fileName,
                        contentType = responseModel.contentType,
                        vm::getFileUriByName
                    )
                }

                ResponseSegmentedButtonState.SOURCE -> {
                    val languageType = contentTypeToLanguageType(responseModel.contentType)
                    ResponseScreenSource(
                        responseModel.fileName, languageType, vm::getFileUriByName
                    )
                }

                ResponseSegmentedButtonState.HEADERS -> {
                    ResponseScreenHeaders(responseModel.headers)
                }
            }
        }
    }
}
