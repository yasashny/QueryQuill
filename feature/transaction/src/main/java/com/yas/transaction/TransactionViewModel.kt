package com.yas.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.ImmutableList
import com.yas.model.RequestModel
import com.yas.model.ResponseModel
import com.yas.model.UpdateRequestModel
import com.yas.requests.local.TransactionsRepository
import com.yas.requests.sendRequest.SendRequestRepository
import com.yas.transaction.navigationDrawer.TransactionsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URI

internal class TransactionViewModel(
    private val transactionsRepository: TransactionsRepository,
    private val sendRequestRepository: SendRequestRepository
) : ViewModel() {

    val transactions = transactionsRepository.getTransactions().map { getTransactionModel ->
        TransactionsUiState.Success(getTransactionModel.list.list, getTransactionModel.currentId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), TransactionsUiState.Loading)

    fun onEvent(transactionEvent: TransactionEvent) {
        when (transactionEvent) {

            is TransactionEvent.DeleteTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionsRepository.deleteTransaction(transactionEvent.id)
                }
            }

            is TransactionEvent.SetTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveRequest()
                    transactionsRepository.changeCurrentTransaction(transactionEvent.id)
                }
            }

            is TransactionEvent.UpdateTransaction -> {
                viewModelScope.launch(Dispatchers.IO) {
                    transactionsRepository.updateTransaction(transactionEvent.transaction)
                }
            }
        }
    }

    private val _requestState = MutableStateFlow<RequestUiState>(RequestUiState.Loading)
    val requestState = _requestState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            transactionsRepository.getCurrentRequestOrNull().map { requestOrNull ->
                if (requestOrNull != null) {
                    RequestUiState.Success(request = requestOrNull)
                } else {
                    RequestUiState.NewRequest
                }
            }.collect { value ->
                _requestState.value = value
            }
        }
    }

    fun sendRequest(requestModel: RequestModel, requestSent: () -> Unit) {
        viewModelScope.launch {
            sendRequestRepository.sendRequest(requestModel)
            requestSent()
        }
    }

    fun saveRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val request = requestState.value) {
                RequestUiState.Loading -> {}
                is RequestUiState.Success -> {
                    transactionsRepository.updateRequest(request.request)
                }

                RequestUiState.NewRequest -> {}
            }
        }
    }

    fun updateRequest(updateRequestModel: UpdateRequestModel) {
        when (val request = requestState.value) {
            RequestUiState.Loading -> {}
            is RequestUiState.Success -> {
                when (updateRequestModel) {
                    is UpdateRequestModel.Body -> {
                        _requestState.value =
                            RequestUiState.Success(request.request.copy(bodyState = updateRequestModel.bodyState))
                    }

                    is UpdateRequestModel.Header -> {
                        _requestState.value = RequestUiState.Success(
                            request.request.copy(
                                header = ImmutableList(
                                    updateRequestModel.header
                                )
                            )
                        )
                    }

                    is UpdateRequestModel.Query -> {
                        _requestState.value = RequestUiState.Success(
                            request.request.copy(
                                query = ImmutableList(
                                    updateRequestModel.query
                                )
                            )
                        )
                    }

                    is UpdateRequestModel.Type -> {
                        _requestState.value =
                            RequestUiState.Success(request.request.copy(type = updateRequestModel.type))
                    }

                    is UpdateRequestModel.Url -> {
                        _requestState.value =
                            RequestUiState.Success(request.request.copy(url = updateRequestModel.url))
                    }

                    is UpdateRequestModel.Auth -> {
                        _requestState.value =
                            RequestUiState.Success(request.request.copy(auth = updateRequestModel.authState))
                    }
                }
            }

            RequestUiState.NewRequest -> {}
        }
    }

    val responseModel = transactionsRepository.getCurrentResponseOrNull().map { responseOrNull ->
        responseOrNull ?: ResponseModel.default()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ResponseModel.default())

    fun getFileUriByName(textFileName: String): URI {
        return transactionsRepository.getFileUriByName(textFileName)
    }
}