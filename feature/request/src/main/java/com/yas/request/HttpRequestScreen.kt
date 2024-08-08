package com.yas.request

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yas.model.KeyValue
import com.yas.model.RequestModel
import com.yas.request.alertDialog.ChangeContentTypeDialog
import com.yas.request.alertDialog.LoadingAlertDialog
import com.yas.request.auth.authScreen
import com.yas.request.body.bodyScreen
import com.yas.request.header.headerScreen
import com.yas.request.query.queryScreen
import com.yas.request.requestScreenHeader.HttpRequestHeaderState
import com.yas.request.requestScreenHeader.HttpRequestScreenHeader
import com.yas.request.utils.Constants
import com.yas.request.utils.changeContentType
import com.yas.utils.vibration
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun HttpRequestScreen(
    requestModel: RequestModel,
    updateRequest: (UpdateRequestModel) -> Unit,
    sendRequest: suspend (RequestModel) -> Unit,
    pagerState: PagerState? = null,
    navigateToEditor: () -> Unit
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
            UpdateRequestModel.Header(listOf(
                KeyValue(
                    Constants.CONTENT_TYPE, openChangeContentTypeDialog.value.second
                )
            ) + requestModel.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
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
            vibration(context)
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
        }, text = { Text(text = stringResource(R.string.send_request)) })
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
                    HttpRequestHeaderState.BODY -> bodyScreen(
                        bodyState = requestModel.bodyState, navigateToEditor = navigateToEditor
                    ) { bodyState ->
                        changeContentType(
                            requestModel = requestModel,
                            updateRequest = updateRequest,
                            bodyState = bodyState,
                            openChangeContentTypeDialog = openChangeContentTypeDialog,
                            context = context
                        )
                        updateRequest(UpdateRequestModel.Body(bodyState))
                    }

                    HttpRequestHeaderState.AUTH -> authScreen(requestModel.auth) { authState ->
                        updateRequest(UpdateRequestModel.Auth(authState))
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