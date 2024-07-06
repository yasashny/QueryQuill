package com.yas.queryquill.screens.responseScreens.httpResponseScreen.preview

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.yas.queryquill.screens.responseScreens.ResponseScreenSource


@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ResponseScreenPreview(body: ByteArray, contentType: String?, contentSubtype: String?) {

    when (contentType) {
        null -> {
            ResponseScreenSource(body.decodeToString())
        }

        "image" -> {
            Base64ImageDisplay(java.util.Base64.getEncoder().encodeToString(body))
        }

        else -> {
            ResponseScreenSource(body.decodeToString())
        }
    }

}