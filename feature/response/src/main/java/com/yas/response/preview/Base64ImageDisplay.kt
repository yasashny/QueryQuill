package com.yas.response.preview

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.yas.model.LanguageType
import com.yas.response.ResponseScreenSource

@Composable
internal fun Base64ImageDisplay(base64String: String, utf8String: String) {
    val bitmap = base64String.toBitmap()
    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    } else {
        ResponseScreenSource(text = utf8String, languageType = LanguageType.PLAIN)
    }


}

private fun String.toBitmap(): android.graphics.Bitmap? {
    return try {
        val decodedBytes = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}