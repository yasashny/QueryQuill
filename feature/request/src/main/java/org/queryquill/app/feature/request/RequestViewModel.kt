package org.queryquill.app.feature.request

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.ImmutableUri
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.data.requests.local.TransactionsRepository
import org.queryquill.app.data.requests.sendRequest.SendRequestRepository
import org.queryquill.app.feature.request.auth.EnumAuthState
import org.queryquill.app.feature.request.body.EnumBodyState
import org.queryquill.app.feature.request.utils.Constants
import org.queryquill.app.feature.request.utils.toMimeType

internal class RequestViewModel(
    private val transactionsRepository: TransactionsRepository,
    private val sendRequestRepository: SendRequestRepository
) : ViewModel() {
    private val _requestState = MutableStateFlow<RequestUiState>(RequestUiState.Loading)
    val requestState = _requestState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            transactionsRepository.getCurrentRequestOrNull().map { requestOrNull ->
                if (requestOrNull != null) {
                    RequestUiState.Success(request = requestOrNull)
                } else {
                    RequestUiState.Loading
                }
            }.collect { value ->
                _requestState.value = value
            }
        }
    }

    private var sendRequestScope: CoroutineScope? = null

    fun sendRequest(onRequestSent: () -> Unit) {
        when (val state = requestState.value) {
            RequestUiState.Loading -> {}
            is RequestUiState.Success -> {
                sendRequestScope = CoroutineScope(Job() + Dispatchers.IO)
                sendRequestScope?.launch {
                    sendRequestRepository.sendRequest(state.request)
                    onRequestSent()
                }
            }
        }
    }

    fun cancelRequest(onRequestSent: () -> Unit) {
        sendRequestScope?.cancel()
        onRequestSent()
    }

    fun saveRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = requestState.value) {
                RequestUiState.Loading -> {}
                is RequestUiState.Success -> {
                    transactionsRepository.updateRequest(state.request)
                }
            }
        }
    }

    fun updateRequest(updateRequestModel: UpdateRequestModel) {
        when (val request = requestState.value) {
            RequestUiState.Loading -> {}
            is RequestUiState.Success -> {
                when (updateRequestModel) {
                    is UpdateRequestModel.Auth.Basic -> {
                        _requestState.update {
                            RequestUiState.Success(request.request.copy(auth = updateRequestModel.basicState))
                        }
                    }

                    is UpdateRequestModel.Body.BinaryFile -> {
                        if (updateRequestModel.isChangeContentType) {
                            _requestState.update {
                                RequestUiState.Success(
                                    request.request.copy(
                                        header = ImmutableList(listOf(
                                            KeyValue(
                                                Constants.CONTENT_TYPE,
                                                updateRequestModel.contentType
                                            )
                                        ) + request.request.header.list.filter { keyValue ->
                                            keyValue.key != Constants.CONTENT_TYPE
                                        }), bodyState = BodyState.BinaryFile(
                                            ImmutableUri(updateRequestModel.uri),
                                            updateRequestModel.fileName
                                        )
                                    )
                                )
                            }
                        } else {
                            _requestState.update {
                                RequestUiState.Success(
                                    request.request.copy(
                                        bodyState = BodyState.BinaryFile(
                                            ImmutableUri(updateRequestModel.uri),
                                            updateRequestModel.fileName
                                        )
                                    )
                                )
                            }
                        }
                    }

                    is UpdateRequestModel.Body.ChangeType -> {
                        when (updateRequestModel.newState) {
                            EnumBodyState.NoBody -> {
                                _requestState.update {
                                    RequestUiState.Success(
                                        request.request.copy(
                                            bodyState = BodyState.NoBody,
                                            header = ImmutableList(request.request.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
                                        )
                                    )
                                }
                            }

                            EnumBodyState.Text -> {
                                _requestState.update {
                                    RequestUiState.Success(
                                        request.request.copy(
                                            bodyState = BodyState.Text.default(
                                                request.request.id
                                            ), header = ImmutableList(listOf(
                                                KeyValue(
                                                    Constants.CONTENT_TYPE,
                                                    BodyState.Text.default(request.request.id).textType.toMimeType()
                                                )
                                            ) + request.request.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
                                        )
                                    )
                                }
                            }

                            EnumBodyState.FormUrlEncoded -> {
                                _requestState.update {
                                    RequestUiState.Success(
                                        request.request.copy(
                                            bodyState = BodyState.FormUrlEncoded.default(),
                                            header = ImmutableList(listOf(
                                                KeyValue(
                                                    Constants.CONTENT_TYPE,
                                                    "application/x-www-form-urlencoded"
                                                )
                                            ) + request.request.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
                                        )
                                    )
                                }
                            }

                            EnumBodyState.MultipartForm -> {
                                _requestState.update {
                                    RequestUiState.Success(
                                        request.request.copy(
                                            bodyState = BodyState.MultipartForm.default(),
                                            header = ImmutableList(listOf(
                                                KeyValue(
                                                    Constants.CONTENT_TYPE,
                                                    "multipart/form-data"
                                                )
                                            ) + request.request.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
                                        )
                                    )
                                }
                            }

                            EnumBodyState.BinaryFile -> {
                                _requestState.update {
                                    RequestUiState.Success(
                                        request.request.copy(
                                            bodyState = BodyState.BinaryFile.default(),
                                            header = ImmutableList(listOf(
                                                KeyValue(
                                                    Constants.CONTENT_TYPE,
                                                    "application/octet-stream"
                                                )
                                            ) + request.request.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
                                        )
                                    )
                                }
                            }
                        }
                    }

                    is UpdateRequestModel.Auth.ChangeType -> {
                        when (updateRequestModel.authState) {
                            EnumAuthState.NoAuth -> {
                                _requestState.update {
                                    RequestUiState.Success(request.request.copy(auth = AuthState.NoAuth))
                                }

                            }

                            EnumAuthState.Basic -> {
                                _requestState.update {
                                    RequestUiState.Success(request.request.copy(auth = AuthState.Basic.default()))
                                }
                            }
                        }
                    }

                    is UpdateRequestModel.Type -> {
                        _requestState.update {
                            RequestUiState.Success(request.request.copy(type = updateRequestModel.type))
                        }
                    }

                    is UpdateRequestModel.Url -> {
                        _requestState.update {
                            RequestUiState.Success(request.request.copy(url = updateRequestModel.url))
                        }
                    }

                    is UpdateRequestModel.Body.FormUrlEncoded -> {
                        _requestState.update {
                            RequestUiState.Success(
                                request.request.copy(
                                    bodyState = BodyState.FormUrlEncoded(
                                        list = ImmutableList(updateRequestModel.list)
                                    )
                                )
                            )
                        }
                    }

                    is UpdateRequestModel.Header -> {
                        _requestState.update {
                            RequestUiState.Success(
                                request.request.copy(
                                    header = ImmutableList(
                                        updateRequestModel.list
                                    )
                                )
                            )
                        }
                    }

                    is UpdateRequestModel.Body.MultipartForm -> {
                        _requestState.update {
                            RequestUiState.Success(
                                request.request.copy(
                                    bodyState = BodyState.MultipartForm(
                                        ImmutableList(updateRequestModel.list)
                                    )
                                )
                            )
                        }
                    }

                    is UpdateRequestModel.Query -> {
                        _requestState.update {
                            RequestUiState.Success(
                                request.request.copy(
                                    query = ImmutableList(
                                        updateRequestModel.list
                                    )
                                )
                            )
                        }
                    }

                    is UpdateRequestModel.Body.UpdateTextType -> {
                        ((requestState.value as? RequestUiState.Success)?.request?.bodyState as? BodyState.Text)?.let { textBodyState ->
                            _requestState.update {
                                RequestUiState.Success(
                                    request.request.copy(
                                        bodyState = textBodyState.copy(textType = updateRequestModel.textType),
                                        header = ImmutableList(listOf(
                                            KeyValue(
                                                Constants.CONTENT_TYPE,
                                                updateRequestModel.textType.toMimeType()
                                            )
                                        ) + request.request.header.list.filter { keyValue -> keyValue.key != Constants.CONTENT_TYPE })
                                    )
                                )
                            }
                        }

                    }
                }
            }
        }
    }

    var screenState by mutableStateOf(ScreenState.BODY)

    fun updateScreenState(newScreenState: ScreenState) {
        screenState = newScreenState
    }

    override fun onCleared() {
        super.onCleared()
        sendRequestScope?.cancel()
    }
}