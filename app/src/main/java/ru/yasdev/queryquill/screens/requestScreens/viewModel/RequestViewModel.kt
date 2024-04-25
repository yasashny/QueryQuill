package ru.yasdev.queryquill.screens.requestScreens.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.yasdev.domain.lastRequest.useCases.GetLastRequestIdUseCase
import ru.yasdev.domain.lastRequest.useCases.SaveLastRequestIdUseCase
import ru.yasdev.domain.requestsDb.models.ImmutableList
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.useCases.AddRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.DeleteRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.GetListOfRequestsUseCase
import ru.yasdev.domain.requestsDb.useCases.GetRequestUseCase
import ru.yasdev.domain.requestsDb.useCases.UpdateRequestUseCase
import ru.yasdev.domain.sendRequest.RequestResponseModel
import ru.yasdev.domain.sendRequest.ResponseModel
import ru.yasdev.domain.sendRequest.SendRequestUseCase
import ru.yasdev.queryquill.navigationDrawer.ListOfRequestsState

@OptIn(ExperimentalCoroutinesApi::class)
class RequestViewModel(
    private val getRequestUseCase: GetRequestUseCase,
    getListOfRequestsUseCase: GetListOfRequestsUseCase,
    private val addRequestUseCase: AddRequestUseCase,
    private val deleteRequestUseCase: DeleteRequestUseCase,
    private val updateRequestUseCase: UpdateRequestUseCase,
    private val saveLastRequestIdUseCase: SaveLastRequestIdUseCase,
    private val getLastRequestIdUseCase: GetLastRequestIdUseCase,
    private val sendRequestUseCase: SendRequestUseCase
) : ViewModel() {

    private val _requestState = MutableStateFlow<RequestState>(RequestState.Loading)
    val requestState = _requestState.asStateFlow()

    private val _requestModel = MutableStateFlow(RequestModel.default())
    val requestModel = _requestModel.asStateFlow()

    private val _responseState = MutableStateFlow(ResponseModel.default())
    val responseState = _responseState.asStateFlow()

    suspend fun sendRequest(requestModel: RequestModel){
        println("www")
        _responseState.value = sendRequestUseCase.execute(requestModel)

    }

    val listOfRequests = getListOfRequestsUseCase.execute().flatMapLatest { list ->
        flow { emit(ListOfRequestsState.ListOfRequests(list)) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ListOfRequestsState.Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLastRequestIdUseCase.execute().collect {
                if (it == null) {
                    _requestState.value = RequestState.Null
                } else {
                    val request = getRequestUseCase.execute(it)
                    _requestModel.value = request.request
                    _responseState.value = request.response
                    _requestState.value = RequestState.Request
                }
            }
        }
    }

    fun onEvent(requestEvent: RequestEvent) {
        when (requestEvent) {
            is RequestEvent.AddRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val newRequest = addRequestUseCase.execute(requestEvent.model)
                    _requestModel.value = newRequest.request
                    _responseState.value = newRequest.response
                    _requestState.value = RequestState.Request
                }
            }

            is RequestEvent.DeleteRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (requestEvent.id == requestModel.value.id) {
                        _requestState.value = RequestState.Null
                        _responseState.value = ResponseModel.default()
                    }
                    deleteRequestUseCase.execute(requestEvent.id)
                }
            }

            is RequestEvent.SetRequest -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (requestEvent.id == null) {
                        if (requestState.value == RequestState.Request) {
                            updateRequestUseCase.execute(RequestResponseModel(requestModel.value, responseState.value))
                        }
                        _requestState.value = RequestState.Null
                        _responseState.value = ResponseModel.default()
                    } else {
                        if (requestState.value == RequestState.Request) {
                            updateRequestUseCase.execute(RequestResponseModel(requestModel.value, responseState.value))
                        }
                        val getRequest = getRequestUseCase.execute(requestEvent.id)
                        _requestModel.value = getRequest.request
                        _responseState.value = getRequest.response
                        _requestState.value = RequestState.Request
                    }
                }
            }
        }
    }

    fun updateHttpRequest(updateHttpRequestModel: UpdateHttpRequestModel) {
        when (updateHttpRequestModel) {
            is UpdateHttpRequestModel.Body -> {
                _requestModel.value =
                    _requestModel.value.copy(bodyState = updateHttpRequestModel.bodyState)
            }

            is UpdateHttpRequestModel.Header -> {
                _requestModel.value =
                    _requestModel.value.copy(header = ImmutableList(updateHttpRequestModel.header))
            }

            is UpdateHttpRequestModel.Query -> {
                _requestModel.value =
                    _requestModel.value.copy(query = ImmutableList(updateHttpRequestModel.query))
            }

            is UpdateHttpRequestModel.Type -> {
                _requestModel.value = _requestModel.value.copy(type = updateHttpRequestModel.type)
            }

            is UpdateHttpRequestModel.Url -> {
                _requestModel.value = _requestModel.value.copy(url = updateHttpRequestModel.url)
            }

            is UpdateHttpRequestModel.Label -> {
                _requestModel.value = _requestModel.value.copy(label = updateHttpRequestModel.label)
                saveLastRequest()
            }

            is UpdateHttpRequestModel.Auth -> {
                _requestModel.value =
                    _requestModel.value.copy(auth = updateHttpRequestModel.authState)
            }
        }
    }

    fun saveLastRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            if (requestState.value == RequestState.Request) {
                updateRequestUseCase.execute(RequestResponseModel(requestModel.value, responseState.value))
                saveLastRequestIdUseCase.execute(requestModel.value.id)
            } else {
                saveLastRequestIdUseCase.execute(null)
            }
        }
    }
}