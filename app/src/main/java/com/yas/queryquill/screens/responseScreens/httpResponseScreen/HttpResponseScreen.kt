package com.yas.queryquill.screens.responseScreens.httpResponseScreen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.yas.domain.sendRequest.ResponseModel

@Composable
fun HttpResponseScreen(
    modifier: Modifier, responseModel: ResponseModel
) {
    Box(modifier = modifier) {
        Column(Modifier.fillMaxSize()) {
            Row {
                OutlinedCard(
                    Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(7.dp),
                            text = "Status: ${responseModel.status}",
                        )
                    }
                }
                OutlinedCard(
                    Modifier.padding(end = 15.dp, top = 15.dp, bottom = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(7.dp),
                            text = "Time: ${responseModel.time} ms",
                        )
                    }
                }
                OutlinedCard(
                    Modifier.padding(end = 15.dp, top = 15.dp, bottom = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            modifier = Modifier.padding(7.dp),
                            text = "${responseModel.contentLength} byte",
                        )
                    }
                }
            }

            var responseState by remember {
                mutableStateOf(ResponseState.PREVIEW)
            }
            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                ) {
                    SegmentedButtonResponse(
                        currentState = responseState,
                        options = listOf(ResponseState.PREVIEW, ResponseState.SOURCE)
                    ) { newState ->
                        if (newState != responseState) {
                            responseState = newState
                        }
                    }
                }

            }

            when (responseState) {
                ResponseState.PREVIEW -> {
                    WebViewPage(html = responseModel.body)
                }

                ResponseState.SOURCE -> {
                    SelectionContainer {
                        val textChunks by remember {
                            mutableStateOf(responseModel.body.chunked(1000))
                        }
                        LazyColumn {
                            items(textChunks) { chunk ->
                                Text(text = chunk)
                            }
                        }
                    }
                }
            }
        }
    }
}
@SuppressLint("SetJavaScriptEnabled", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WebViewPage(html: String) {
    Scaffold {
        val context = LocalContext.current
        val webView = remember {
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { webView }, update = {
            it.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
        })
    }
}

