package com.yas.response

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.yas.response.components.ScreenBar
import com.yas.response.components.ScreenContent
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

        ScreenBar(
            status = responseModel.status,
            time = responseModel.time,
            contentLength = responseModel.contentLength,
            file = responseFile
        )

        ScreenContent(
            contentType = responseModel.contentType,
            file = responseFile,
            headers = responseModel.headers,
            segmentedButtonState = vm.segmentedButtonState,
            updateSegmentedButtonState = vm::updateSegmentedButtonState,
            codeEditorState = vm.codeEditorState
        )
    }
}