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

package org.queryquill.app.feature.response.preview

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.feature.response.source.ResponseScreenSource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun Base64ImageDisplay(
    fileUri: Uri, codeEditorState: CodeEditorState, fileLength: Long,
    transferFileToCodeEditorState: () -> Unit,
    codeEditorLoadingState: Boolean,
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(fileUri).size(Size.ORIGINAL)
                .crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        ) {
            val state = painter.state
            when (state) {
                AsyncImagePainter.State.Empty -> {}
                is AsyncImagePainter.State.Error -> {
                    ResponseScreenSource(
                        LanguageType.PLAIN,
                        codeEditorState,
                        fileLength,
                        transferFileToCodeEditorState,
                        codeEditorLoadingState
                    )
                }

                is AsyncImagePainter.State.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingIndicator()
                    }
                }

                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}