package ru.yasdev.queryquill.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.useCases.AddRequestUseCase
import ru.yasdev.domain.useCases.DeleteRequestUseCase
import ru.yasdev.domain.useCases.GetLastRequestIdUseCase
import ru.yasdev.domain.useCases.GetListOfRequestsUseCase
import ru.yasdev.domain.useCases.GetRequestUseCase
import ru.yasdev.domain.useCases.SaveLastRequestIdUseCase
import ru.yasdev.domain.useCases.UpdateRequestUseCase
import ru.yasdev.domain.utils.RequestState
import ru.yasdev.domain.utils.ListOfRequestsState

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModel(
    private val getRequestUseCase: GetRequestUseCase,
    private val getListOfRequestsUseCase: GetListOfRequestsUseCase,
    private val addRequestUseCase: AddRequestUseCase,
    private val deleteRequestUseCase: DeleteRequestUseCase,
    private val updateRequestUseCase: UpdateRequestUseCase,
    private val saveLastRequestIdUseCase: SaveLastRequestIdUseCase,
    private val getLastRequestIdUseCase: GetLastRequestIdUseCase
    ): ViewModel() {

    private val _requestState = MutableStateFlow<RequestState>(RequestState.Loading)

//    val request = _requestState.flatMapLatest { id ->
//        when(id){
//            RequestState.Loading -> flow { emit(RequestStateqqqqqqq.Loading) }
//            RequestState.Null -> flow { emit(RequestStateqqqqqqq.NullRequest) }
//            is RequestState.Id -> getRequestUseCase.execute(id.id).flatMapLatest { requestModel ->
//                flow { emit(RequestStateqqqqqqq.Request(requestModel)) }
//            }
//        }
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), RequestState.Loading)

    val requestState = _requestState.asStateFlow()

    private val _requestModel = MutableStateFlow<RequestModel>(RequestModel(-1, "-1", "-1"))
    val requestModel = _requestModel.asStateFlow()

    val listOfRequests = getListOfRequestsUseCase.execute().flatMapLatest { list ->
            flow{emit(ListOfRequestsState.ListOfRequests(list))}
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ListOfRequestsState.Loading)

    init {
        viewModelScope.launch {
            getLastRequestIdUseCase.execute().collect{
                if (it == null){
                    _requestState.value = RequestState.Null
                }
                else{
                    val request = getRequestUseCase.execute(it)
                    _requestModel.value= request
                    _requestState.value = RequestState.Request

                }
            }
        }
    }

    fun onEvent(requestEvent: RequestEvent){
        when(requestEvent){
            is RequestEvent.AddRequest -> {
                viewModelScope.launch {
                    //_requestState.value = RequestState.Loading
                    val newRequest = addRequestUseCase.execute(requestEvent.model)
                    _requestModel.value = newRequest
                    _requestState.value = RequestState.Request
                }
            }
            is RequestEvent.DeleteRequest -> {
                viewModelScope.launch{

                    if(requestEvent.id == requestModel.value.id){
                        _requestState.value = RequestState.Null
                    }
                    deleteRequestUseCase.execute(requestEvent.id)


                }
            }
            is RequestEvent.SetRequest -> {
                viewModelScope.launch{

                    if (requestEvent.id == null){
                        if(requestState.value == RequestState.Request){
                            updateRequestUseCase.execute(requestModel.value)
                        }
                        _requestState.value = RequestState.Null
                    }
                    else{
                        //_requestState.value = RequestState.Loading
                        if(requestState.value == RequestState.Request){
                            updateRequestUseCase.execute(requestModel.value)
                        }
                        _requestModel.value = getRequestUseCase.execute(requestEvent.id)
                        _requestState.value = RequestState.Request
                    }

                }
            }
        }

    }

    fun save(){
        viewModelScope.launch {
            if (requestState.value == RequestState.Request){
                updateRequestUseCase.execute(requestModel.value)
                saveLastRequestIdUseCase.execute(requestModel.value.id)
            }
            else{
                saveLastRequestIdUseCase.execute(null)
            }


        }
    }








    private val _counter = MutableStateFlow("")
    val counter = _counter.asStateFlow()

    fun incrementCounter(text: String) {
        _counter.value = text
    }

    fun qqq(){
//        viewModelScope.launch {
//            listOfRequests.collect(){
//                Log.d("Hello", it.toString())
//            }
//
//        }

    }



}