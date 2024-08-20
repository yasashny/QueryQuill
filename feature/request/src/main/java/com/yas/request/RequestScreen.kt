package com.yas.request

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.yas.model.KeyValue
import com.yas.model.UpdateRequestModel
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
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RequestScreen(
    modifier: Modifier,
    navigateToEditor: (textFileName: String, languageType: String) -> Unit,
    onRequestSent: () -> Unit
) {
    Box(modifier = modifier) {
        val vm = koinViewModel<RequestViewModel>()
        val requestUiState = vm.requestState.collectAsState().value

        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    vm.saveRequest(requestUiState)
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        when (requestUiState) {
            RequestUiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is RequestUiState.Success -> {
                val requestModel = requestUiState.request
                val context = LocalContext.current
                val openChangeContentTypeDialog = remember {
                    mutableStateOf(Pair(false, ""))
                }
                if (openChangeContentTypeDialog.value.first) {
                    ChangeContentTypeDialog(newContentType = openChangeContentTypeDialog.value.second,
                        onDismiss = {
                            openChangeContentTypeDialog.value =
                                Pair(false, openChangeContentTypeDialog.value.second)
                        },
                        onConfirm = {
                            vm.updateRequest(
                                UpdateRequestModel.Header(listOf(
                                    KeyValue(
                                        Constants.CONTENT_TYPE,
                                        openChangeContentTypeDialog.value.second
                                    )
                                ) + requestModel.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
                            )
                            openChangeContentTypeDialog.value =
                                Pair(false, openChangeContentTypeDialog.value.second)
                        })
                }
                var openLoadingDialog by remember {
                    mutableStateOf(false)
                }
                if (openLoadingDialog) {
                    LoadingAlertDialog {
                        vm.cancelRequest(onRequestSent)
                        openLoadingDialog = false
                    }
                }
                Scaffold(floatingActionButton = {
                    ExtendedFloatingActionButton(onClick = {
                        vibration(context)
                        openLoadingDialog = true
                        vm.sendRequest(requestModel) {
                            openLoadingDialog = false
                            onRequestSent()
                        }
                    }, icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Send,
                            contentDescription = null
                        )
                    }, text = { Text(text = stringResource(R.string.send_request)) })
                }) {
                    Box {
                        val httpRequestHeaderState =
                            remember { mutableStateOf(HttpRequestHeaderState.BODY) }
                        LazyColumn {
                            item {
                                HttpRequestScreenHeader(
                                    requestModel.type,
                                    requestModel.url,
                                    vm::updateRequest,
                                    httpRequestHeaderState
                                )
                            }
                            when (httpRequestHeaderState.value) {
                                HttpRequestHeaderState.BODY -> bodyScreen(
                                    bodyState = requestModel.bodyState,
                                    getTextFileUri = vm::getFileUriByName,
                                    requestId = requestModel.id,
                                    navigateToEditor = navigateToEditor
                                ) { bodyState ->
                                    changeContentType(
                                        requestModel = requestModel,
                                        updateRequest = vm::updateRequest,
                                        bodyState = bodyState,
                                        openChangeContentTypeDialog = openChangeContentTypeDialog,
                                        context = context
                                    )
                                    vm.updateRequest(UpdateRequestModel.Body(bodyState))
                                }

                                HttpRequestHeaderState.AUTH -> authScreen(requestModel.auth) { authState ->
                                    vm.updateRequest(UpdateRequestModel.Auth(authState))
                                }

                                HttpRequestHeaderState.HEADER -> headerScreen(
                                    requestModel, vm::updateRequest
                                )

                                HttpRequestHeaderState.QUERY -> queryScreen(
                                    requestModel, vm::updateRequest
                                )
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
        }
    }
}