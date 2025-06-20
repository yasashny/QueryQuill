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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.TextType
import org.queryquill.app.core.ui.SaveDataOnStop
import org.queryquill.app.feature.request.auth.authScreen
import org.queryquill.app.feature.request.body.bodyScreen
import org.queryquill.app.feature.request.components.GroupButtons
import org.queryquill.app.feature.request.components.QueryPreview
import org.queryquill.app.feature.request.components.ScreenBar
import org.queryquill.app.feature.request.components.editableList
import org.queryquill.app.feature.request.dialog.LoadingDialog

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
    RequestScreen(
        modifier = modifier,
        navigateToEditor = navigateToEditor,
        onRequestSent = onRequestSent,
        requestUiState = requestUiState,
        screenState = vm.screenState,
        cancelRequest = vm::cancelRequest,
        sendRequest = vm::sendRequest,
        updateScreenState = vm::updateScreenState,
        onEvent = vm::onEvent
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun RequestScreen(
    modifier: Modifier,
    navigateToEditor: (fileName: String, textType: TextType) -> Unit,
    onRequestSent: () -> Unit,
    requestUiState: RequestUiState,
    screenState: ScreenState,
    cancelRequest: (onRequestSent: () -> Unit) -> Unit,
    sendRequest: (onRequestSent: () -> Unit) -> Unit,
    updateScreenState: (ScreenState) -> Unit,
    onEvent: (UpdateRequest) -> Unit,

    ) {
    Box(modifier) {
        AnimatedContent(
            targetState = requestUiState,
            contentKey = { state: RequestUiState -> state::class },
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "RequestScreen"
        ) { state ->
            when (state) {
                RequestUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        LoadingIndicator()
                    }
                }

                is RequestUiState.Success -> {
                    var openLoadingDialog by remember {
                        mutableStateOf(false)
                    }
                    if (openLoadingDialog) {
                        LoadingDialog {
                            cancelRequest(onRequestSent)
                            openLoadingDialog = false
                        }
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        val requestModel = state.request

                        LazyColumn {
                            item("screen_bar") {
                                ScreenBar(
                                    modifier = Modifier.animateItem(fadeOutSpec = null),
                                    type = requestModel.type,
                                    url = requestModel.url,
                                    onTypeChange = {
                                        onEvent(
                                            UpdateRequest.Type(it)
                                        )
                                    },
                                    onUrlChange = {
                                        onEvent(UpdateRequest.Url(it))
                                    }) {
                                    openLoadingDialog = true
                                    sendRequest {
                                        openLoadingDialog = false
                                        onRequestSent()
                                    }
                                }
                            }
                            item("group_buttons") {
                                GroupButtons(
                                    modifier = Modifier
                                        .padding(bottom = Dimens.medium)
                                        .animateItem(fadeOutSpec = null),
                                    screenState = screenState,
                                    updateScreenState = {
                                        updateScreenState(it)
                                    })
                                HorizontalDivider()
                            }
                            when (screenState) {
                                ScreenState.BODY -> bodyScreen(
                                    bodyState = requestModel.bodyState,
                                    navigateToEditor = navigateToEditor,
                                    onBodyEvent = onEvent
                                )

                                ScreenState.AUTH -> authScreen(
                                    state = requestModel.auth, onAuthEvent = onEvent
                                )


                                ScreenState.HEADER -> {
                                    item("header_spacer") {
                                        Spacer(Modifier.padding(Dimens.small))
                                    }
                                    editableList(
                                        items = requestModel.header,
                                        updateRequest = { updateType, item ->
                                            onEvent(
                                                UpdateRequest.Headers(updateType, item)
                                            )
                                        })
                                }

                                ScreenState.QUERY -> {
                                    item("query_preview") {
                                        QueryPreview(
                                            modifier = Modifier
                                                .padding(
                                                    Dimens.medium
                                                )
                                                .animateItem(fadeOutSpec = null),
                                            url = requestModel.url,
                                            query = requestModel.query
                                        )
                                    }
                                    editableList(
                                        items = requestModel.query,
                                        updateRequest = { updateType, item ->
                                            onEvent(
                                                UpdateRequest.Query(updateType, item)
                                            )
                                        })
                                }
                            }
                            item("footer") {
                                Spacer(
                                    modifier = Modifier
                                        .height(150.dp)
                                        .fillMaxWidth()
                                        .animateItem(fadeOutSpec = null)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRequestScreen() {
    QueryQuillTheme {
        RequestScreen(
            modifier = Modifier.fillMaxWidth(),
            navigateToEditor = { _, _ -> },
            onRequestSent = {},
            requestUiState = RequestUiState.Success(
                RequestModel(
                    url = "https://example.com",
                    type = HttpType.GET,
                    bodyState = BodyState.FormUrlEncoded(
                        listOf(
                            KeyValue("key1", "value1"), KeyValue("", "")
                        )
                    ),
                    auth = AuthState.NoAuth,
                    header = emptyList(),
                    query = emptyList(),
                    id = -1
                )
            ),
            cancelRequest = {},
            sendRequest = {},
            screenState = ScreenState.BODY,
            updateScreenState = {},
            onEvent = {})
    }
}