package org.queryquill.app.feature.response

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.feature.response.components.ScreenBar
import org.queryquill.app.feature.response.components.ScreenContent
import java.io.File

@Composable
fun ResponseScreen(
    modifier: Modifier
) {
    Column(modifier = modifier) {
        val context = LocalContext.current
        val vm = koinViewModel<ResponseViewModel>()
        val responseModel = vm.responseModel.collectAsState().value

        val responseFile = remember(responseModel) {
            val file = File(context.filesDir, responseModel.fileName)
            if (!file.exists()) {
                file.writeText("")
            }
            file
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