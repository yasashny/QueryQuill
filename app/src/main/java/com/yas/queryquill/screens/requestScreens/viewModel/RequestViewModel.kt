package com.yas.queryquill.screens.requestScreens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.ImmutableList
import com.yas.model.RequestModel
import com.yas.queryquill.navigationDrawer.ListOfRequestsState
import com.yas.requests.local.RequestsRepository
import com.yas.requests.sendRequest.SendRequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RequestViewModel(
    private val requestsRepository: RequestsRepository,
    private val sendRequestRepository: SendRequestRepository
) : ViewModel() {

    private val _requestState = MutableStateFlow<RequestState>(RequestState.Loading)
    val requestState = _requestState.asStateFlow()

    val listOfRequests = requestsRepository.getListOfRequests().map { list ->
        ListOfRequestsState.ListOfRequests(list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ListOfRequestsState.Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            requestsRepository.getCurrentRequestOrNull().map { requestOrNull ->
                if (requestOrNull != null) {
                    RequestState.Request(request = requestOrNull)
                } else {
                    RequestState.NewRequest
                }
            }.collect { value ->
                _requestState.value = value
            }
        }
    }

    suspend fun sendRequest(requestModel: RequestModel) {
        sendRequestRepository.sendRequest(requestModel)
    }

    fun onEvent(requestEvent: RequestEvent) {
        when (requestEvent) {
            is RequestEvent.AddRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    requestsRepository.addRequest(requestEvent.model)
                }
            }

            is RequestEvent.DeleteRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    requestsRepository.deleteRequest(requestEvent.id)
                }
            }

            is RequestEvent.SetRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    onEvent(RequestEvent.SaveRequest)
                    requestsRepository.changeCurrentRequestId(requestEvent.id)
                }
            }

            RequestEvent.SaveRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    when (val request = requestState.value) {
                        RequestState.Loading -> {}
                        RequestState.NewRequest -> {}
                        is RequestState.Request -> {
                            requestsRepository.updateRequest(request.request)
                        }
                    }
                }
            }
        }
    }

    fun updateRequest(updateRequestModel: UpdateRequestModel) {
        when (val request = requestState.value) {
            RequestState.Loading -> {}
            RequestState.NewRequest -> {}
            is RequestState.Request -> {
                when (updateRequestModel) {
                    is UpdateRequestModel.Body -> {
                        _requestState.value =
                            RequestState.Request(request.request.copy(bodyState = updateRequestModel.bodyState))
                    }

                    is UpdateRequestModel.Header -> {
                        _requestState.value = RequestState.Request(
                            request.request.copy(
                                header = ImmutableList(
                                    updateRequestModel.header
                                )
                            )
                        )
                    }

                    is UpdateRequestModel.Query -> {
                        _requestState.value = RequestState.Request(
                            request.request.copy(
                                query = ImmutableList(
                                    updateRequestModel.query
                                )
                            )
                        )
                    }

                    is UpdateRequestModel.Type -> {
                        _requestState.value =
                            RequestState.Request(request.request.copy(type = updateRequestModel.type))
                    }

                    is UpdateRequestModel.Url -> {
                        _requestState.value =
                            RequestState.Request(request.request.copy(url = updateRequestModel.url))
                    }

                    is UpdateRequestModel.Label -> {
                        _requestState.value =
                            RequestState.Request(request.request.copy(label = updateRequestModel.label))
                        onEvent(RequestEvent.SaveRequest)
                    }

                    is UpdateRequestModel.Auth -> {
                        _requestState.value =
                            RequestState.Request(request.request.copy(auth = updateRequestModel.authState))
                    }
                }
            }
        }
    }
}