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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.queryquill.app.core.data.FileRepository
import org.queryquill.app.core.data.SendRequestRepository
import org.queryquill.app.core.data.TransactionRepository
import org.queryquill.app.core.model.HttpType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.RequestModel

internal class RequestViewModel(
    private val transactionRepository: TransactionRepository,
    private val sendRequestRepository: SendRequestRepository,
    private val fileRepository: FileRepository
) : ViewModel() {
    private val _requestState = MutableStateFlow<RequestUiState>(RequestUiState.Loading)
    val requestState = _requestState.onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), RequestUiState.Loading)

    private val RequestUiState.requestOrNull: RequestModel?
        get() = (this as? RequestUiState.Success)?.request

    private fun loadData() {
        viewModelScope.launch {
            transactionRepository.getCurrentRequestOrNull().map { requestOrNull ->
                if (requestOrNull != null) {
                    RequestUiState.Success(request = requestOrNull)
                } else {
                    RequestUiState.Loading
                }
            }.collect { value ->
                when (val state = requestState.value) {
                    RequestUiState.Loading -> {}
                    is RequestUiState.Success -> {
                        transactionRepository.updateRequest(state.request)
                    }
                }
                _requestState.update { value }
            }
        }
    }

    private var currentSendJob: Job? = null

    fun sendRequest(onRequestSent: () -> Unit) {
        requestState.value.requestOrNull?.let { state ->
            currentSendJob?.cancel()
            currentSendJob = viewModelScope.launch {
                sendRequestRepository.sendRequest(state)
                onRequestSent()
            }
        }
    }

    fun cancelRequest(onRequestSent: () -> Unit) {
        currentSendJob?.cancel()
        onRequestSent()
    }

    fun saveRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            requestState.value.requestOrNull?.let { state ->
                transactionRepository.updateRequest(state)
            }
        }
    }

    var screenState by mutableStateOf(ScreenState.BODY)

    fun updateScreenState(newScreenState: ScreenState) {
        screenState = newScreenState
    }

    fun onEvent(updateRequest: UpdateRequest) {
        when (updateRequest) {
            is UpdateRequest.Auth -> updateAuth(updateRequest)
            is UpdateRequest.Body -> updateBody(updateRequest)
            is UpdateRequest.Headers -> updateHeader(updateRequest.updateType, updateRequest.item)
            is UpdateRequest.Query -> updateQuery(updateRequest.updateType, updateRequest.item)
            is UpdateRequest.Type -> updateType(updateRequest.httpType)
            is UpdateRequest.Url -> updateUrl(updateRequest.url)
        }
    }

    private fun updateHeader(updateType: UpdateRequest.UpdateType, item: KeyValue) {
        requestState.value.requestOrNull?.let { state ->
            _requestState.update {
                RequestUiState.Success(
                    state.copy(
                        header = KeyValueListUpdater.update(updateType, item, state.header)
                    )
                )
            }
        }
    }

    private fun updateQuery(updateType: UpdateRequest.UpdateType, item: KeyValue) {
        requestState.value.requestOrNull?.let { state ->
            _requestState.update {
                RequestUiState.Success(
                    state.copy(
                        query = KeyValueListUpdater.update(updateType, item, state.query)
                    )
                )
            }
        }
    }

    private fun updateUrl(url: String) {
        requestState.value.requestOrNull?.let { state ->
            _requestState.update {
                RequestUiState.Success(
                    state.copy(
                        url = url
                    )
                )
            }
        }
    }

    private fun updateType(type: HttpType) {
        requestState.value.requestOrNull?.let { state ->
            _requestState.update {
                RequestUiState.Success(
                    state.copy(
                        type = type
                    )
                )
            }
        }
    }

    private fun updateBody(updateBody: UpdateRequest.Body) {
        viewModelScope.launch {
            requestState.value.requestOrNull?.let { state ->
                _requestState.update {
                    BodyStateUpdater.updateBody(
                        currentState = state,
                        updateBody = updateBody,
                        fileRepository::getFileLength,
                        fileRepository::deleteFile
                    ) ?: it
                }
            }
        }
    }

    private fun updateAuth(updateAuth: UpdateRequest.Auth) {
        requestState.value.requestOrNull?.let { state ->
            _requestState.update {
                AuthStateUpdater.updateAuth(currentState = state, updateAuth = updateAuth) ?: it
            }
        }
    }
}