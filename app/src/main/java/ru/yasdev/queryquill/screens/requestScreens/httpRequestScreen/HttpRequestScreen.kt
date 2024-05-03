package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.yasdev.domain.requestsDb.models.KeyValue
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.queryquill.components.ChangeContentTypeDialog
import ru.yasdev.queryquill.components.LoadingAlertDialog
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.auth.authScreen
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.body.bodyScreen
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.header.headerScreen
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.query.queryScreen
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.requestScreenHeader.HttpRequestHeaderState
import ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen.requestScreenHeader.HttpRequestScreenHeader
import ru.yasdev.queryquill.screens.requestScreens.viewModel.UpdateHttpRequestModel

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HttpRequestScreen(
    requestModel: RequestModel,
    updateRequest: (UpdateHttpRequestModel) -> Unit,
    sendRequest: suspend (RequestModel) -> Unit,
    pagerState: PagerState? = null
) {
    val context = LocalContext.current
    val openChangeContentTypeDialog = remember {
        mutableStateOf(Pair(false, ""))
    }
    val isChangeContentType = remember {
        mutableStateOf(false)
    }
    if (openChangeContentTypeDialog.value.first) {
        ChangeContentTypeDialog(
            openDialog = openChangeContentTypeDialog, isChangeType = isChangeContentType
        )
    }
    if (isChangeContentType.value) {
        updateRequest(
            UpdateHttpRequestModel.Header(listOf(
                KeyValue(
                    "Content-Type", openChangeContentTypeDialog.value.second
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != "Content-Type" })
        )
        isChangeContentType.value = false
    }
    val scope = rememberCoroutineScope()
    val openLoadingDialog = remember {
        mutableStateOf(false)
    }
    val isCancelJob = remember {
        mutableStateOf(false)
    }
    if (openLoadingDialog.value) {
        LoadingAlertDialog(openDialog = openLoadingDialog, isCancelJob)
    }
    if (isCancelJob.value) {
        scope.cancel()
        isCancelJob.value = false
    }

    Scaffold(floatingActionButton = {
        ExtendedFloatingActionButton(onClick = {
            println("qqq")
            openLoadingDialog.value = true
            scope.launch {
                sendRequest(requestModel)
                openLoadingDialog.value = false
                pagerState?.scrollToPage(2)
            }
        }, icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Send, contentDescription = null
            )
        }, text = { Text(text = "Send request") })
    }) {
        Box {
            val httpRequestHeaderState = remember { mutableStateOf(HttpRequestHeaderState.BODY) }
            LazyColumn {
                item {
                    HttpRequestScreenHeader(
                        requestModel.type, requestModel.url, updateRequest, httpRequestHeaderState
                    )
                }
                when (httpRequestHeaderState.value) {
                    HttpRequestHeaderState.BODY -> bodyScreen(requestModel.bodyState) { bodyState ->
                        changeContentType(
                            requestModel = requestModel,
                            updateRequest = updateRequest,
                            bodyState = bodyState,
                            openChangeContentTypeDialog = openChangeContentTypeDialog,
                            context = context
                        )
                        updateRequest(UpdateHttpRequestModel.Body(bodyState))
                    }

                    HttpRequestHeaderState.AUTH -> authScreen(requestModel.auth) { authState ->
                        updateRequest(UpdateHttpRequestModel.Auth(authState))
                    }

                    HttpRequestHeaderState.HEADER -> headerScreen(requestModel, updateRequest)
                    HttpRequestHeaderState.QUERY -> queryScreen(requestModel, updateRequest)
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}