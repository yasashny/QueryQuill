package com.yas.response.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import com.yas.model.LanguageType
import com.yas.response.ResponseScreenSource
import java.io.File
import java.net.URI

@Composable
internal fun Base64ImageDisplay(
    fileName: String, getTextFileUri: (textFileName: String) -> URI
) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val file = File(getTextFileUri(fileName))
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(file).size(Size.ORIGINAL)
                .crossfade(true).build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        ) {
            val state = painter.state
            when (state) {
                AsyncImagePainter.State.Empty -> {}
                is AsyncImagePainter.State.Error -> {
                    ResponseScreenSource(
                        fileName, LanguageType.PLAIN, getTextFileUri
                    )
                }

                is AsyncImagePainter.State.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}