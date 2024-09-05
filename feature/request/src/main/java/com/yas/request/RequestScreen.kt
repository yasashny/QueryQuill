package com.yas.request

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yas.model.KeyValue
import com.yas.request.alertDialog.LoadingAlertDialog
import com.yas.request.auth.authScreen
import com.yas.request.body.bodyScreen
import com.yas.request.components.ScreenBar
import com.yas.request.components.SegmentedButtonScreenState
import com.yas.request.components.SendRequestButton
import com.yas.request.components.editableList
import com.yas.request.query.queryScreen
import com.yas.request.utils.Constants
import com.yas.request.utils.SaveRequestOnStop
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RequestScreen(
    modifier: Modifier,
    navigateToEditor: (textFileName: String, languageType: String) -> Unit,
    onRequestSent: () -> Unit
) {
    val vm = koinViewModel<RequestViewModel>()
    val requestUiState = vm.requestState.collectAsState().value

    SaveRequestOnStop {
        vm.saveRequest()
    }

    when (requestUiState) {
        RequestUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is RequestUiState.Success -> {

            var openLoadingDialog by remember {
                mutableStateOf(false)
            }
            if (openLoadingDialog) {
                LoadingAlertDialog {
                    vm.cancelRequest(onRequestSent)
                    openLoadingDialog = false
                }
            }

            Scaffold(modifier = modifier, floatingActionButton = {
                SendRequestButton {
                    openLoadingDialog = true
                    vm.sendRequest {
                        openLoadingDialog = false
                        onRequestSent()
                    }
                }
            }) {
                val requestModel = requestUiState.request

                LazyColumn {
                    item {
                        ScreenBar(getType = { requestModel.type },
                            getUrl = { requestModel.url },
                            updateType = {
                                vm.updateRequest(UpdateRequestModel.Type(it))
                            },
                            updateUrl = {
                                vm.updateRequest(UpdateRequestModel.Url(it))
                            })
                    }
                    item {
                        SegmentedButtonScreenState(screenState = vm.screenState) {
                            vm.updateScreenState(it)
                        }
                    }
                    item {
                        HorizontalDivider()
                    }
                    when (vm.screenState) {
                        ScreenState.BODY -> bodyScreen(bodyState = requestModel.bodyState,
                            getTextFileUri = vm::getFileUriByName,
                            requestId = requestModel.id,
                            navigateToEditor = navigateToEditor,
                            changeBodyType = {
                                vm.updateRequest(
                                    UpdateRequestModel.Body.ChangeType(
                                        it
                                    )
                                )
                            },
                            updateTextType = {
                                vm.updateRequest(
                                    UpdateRequestModel.Body.UpdateTextType(
                                        it
                                    )
                                )
                            },
                            updateFormUrlEncoded = {
                                vm.updateRequest(
                                    UpdateRequestModel.Body.FormUrlEncoded(
                                        it
                                    )
                                )
                            },
                            updateMultipartForm = {
                                vm.updateRequest(
                                    UpdateRequestModel.Body.MultipartForm(
                                        it
                                    )
                                )
                            },
                            updateBinaryFile = { uri, fileName, isChangeType, contentType ->
                                vm.updateRequest(
                                    UpdateRequestModel.Body.BinaryFile(
                                        uri = uri,
                                        fileName = fileName,
                                        isChangeContentType = isChangeType,
                                        contentType = contentType
                                    )
                                )
                            },
                            isContentTypeInHeaders = {
                                requestModel.header.list.contains(
                                    KeyValue(
                                        key = Constants.CONTENT_TYPE, value = it
                                    )
                                )
                            })

                        ScreenState.AUTH -> authScreen(getAuthState = { requestModel.auth },
                            changeAuthType = {
                                vm.updateRequest(
                                    UpdateRequestModel.Auth.ChangeType(it)
                                )
                            },
                            updateBasicAuth = { vm.updateRequest(UpdateRequestModel.Auth.Basic(it)) })

                        ScreenState.HEADER -> editableList(items = requestModel.header.list) { keyValueList ->
                            vm.updateRequest(UpdateRequestModel.Header(keyValueList))
                        }

                        ScreenState.QUERY -> queryScreen(getUrl = { requestModel.url },
                            getQuery = { requestModel.query },
                            updateQuery = {
                                vm.updateRequest(UpdateRequestModel.Query(it))
                            })
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