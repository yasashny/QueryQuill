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

package org.queryquill.app.feature.request

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.TextType
import org.queryquill.app.core.ui.SaveDataOnStop
import org.queryquill.app.feature.request.alertDialog.LoadingAlertDialog
import org.queryquill.app.feature.request.auth.authScreen
import org.queryquill.app.feature.request.body.bodyScreen
import org.queryquill.app.feature.request.components.ScreenBar
import org.queryquill.app.feature.request.components.SegmentedButtonScreenState
import org.queryquill.app.feature.request.components.editableList
import org.queryquill.app.feature.request.query.queryScreen
import org.queryquill.app.feature.request.utils.Constants


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RequestScreen(
    modifier: Modifier,
    navigateToEditor: (fileName: String, textType: TextType) -> Unit,
    onRequestSent: () -> Unit
) {
    val vm = koinViewModel<RequestViewModel>()
    val requestUiState = vm.requestState.collectAsStateWithLifecycle().value

    SaveDataOnStop {
        vm.saveRequest()
    }

    when (requestUiState) {
        RequestUiState.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                LoadingIndicator()
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

            Box(modifier = modifier) {
                val requestModel = requestUiState.request

                LazyColumn {
                    item {
                        ScreenBar(
                            getType = { requestModel.type },
                            getUrl = { requestModel.url },
                            updateType = {
                                vm.updateRequest(UpdateRequestModel.Type(it))
                            },
                            updateUrl = {
                                vm.updateRequest(UpdateRequestModel.Url(it))
                            }) {
                            openLoadingDialog = true
                            vm.sendRequest {
                                openLoadingDialog = false
                                onRequestSent()
                            }
                        }
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
                        ScreenState.BODY -> bodyScreen(
                            bodyState = requestModel.bodyState,
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

                        ScreenState.AUTH -> authScreen(
                            getAuthState = { requestModel.auth },
                            changeAuthType = {
                                vm.updateRequest(
                                    UpdateRequestModel.Auth.ChangeType(it)
                                )
                            },
                            updateBasicAuth = { vm.updateRequest(UpdateRequestModel.Auth.Basic(it)) })

                        ScreenState.HEADER -> editableList(items = requestModel.header.list) { keyValueList ->
                            vm.updateRequest(UpdateRequestModel.Header(keyValueList))
                        }

                        ScreenState.QUERY -> queryScreen(
                            getUrl = { requestModel.url },
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