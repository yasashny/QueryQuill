/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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