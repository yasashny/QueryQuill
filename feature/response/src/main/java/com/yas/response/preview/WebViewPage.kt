package com.yas.response.preview

import android.annotation.SuppressLint
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import java.io.File
import java.net.URI


@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun WebViewPage(fileName: String, getTextFileUri: (textFileName: String) -> URI) {
    Scaffold {
        Box {
            val context = LocalContext.current
            val webView = remember {
                WebView(context).apply {
                    settings.allowFileAccess = true
                    settings.javaScriptEnabled = true
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }
            val fileUri = Uri.fromFile(File(getTextFileUri(fileName))).toString()
            AndroidView(modifier = Modifier.fillMaxSize(), factory = { webView }, update = {
                it.loadUrl(fileUri)
            })
        }
    }
}
