package ru.yasdev.queryquill.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
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
import ru.yasdev.domain.utils.LastIdState
import ru.yasdev.domain.utils.ListOfRequestsState
import ru.yasdev.domain.utils.RequestState

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModel(
    private val getRequestUseCase: GetRequestUseCase,
    private val getListOfRequestsUseCase: GetListOfRequestsUseCase,
    private val addRequestUseCase: AddRequestUseCase,
    private val deleteRequestUseCase: DeleteRequestUseCase,
    private val updateRequestUseCase: UpdateRequestUseCase,
    private val saveLastRequestIdUseCase: SaveLastRequestIdUseCase
    ): ViewModel() {

    private val _requestId = MutableStateFlow<LastIdState>(LastIdState.Loading)

    val request = _requestId.flatMapLatest { id ->
        when(id){
            LastIdState.Loading -> flow { emit(RequestState.Loading) }
            LastIdState.Null -> flow { emit(RequestState.NullRequest) }
            is LastIdState.Id -> getRequestUseCase.execute(id.id).flatMapLatest { requestModel ->
                flow { emit(RequestState.Request(requestModel)) }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LastIdState.Loading)

    val listOfRequests = _requestId.flatMapLatest { id ->
        when(id){
            LastIdState.Loading -> getListOfRequestsUseCase.execute().flatMapLatest { list ->
                flow { emit(ListOfRequestsState.ListOfRequests(list)) }
            }
            LastIdState.Null -> getListOfRequestsUseCase.execute().flatMapLatest { list ->
                flow { emit(ListOfRequestsState.ListOfRequests(list)) }
            }
            is LastIdState.Id -> getListOfRequestsUseCase.execute().flatMapLatest { list ->
                flow { emit(ListOfRequestsState.ListOfRequests(list)) }
            }
        }
    }

    val requestId = _requestId.asStateFlow()

    init {
        viewModelScope.launch {

        }
    }

    fun onEvent(requestEvent: RequestEvent){
        when(requestEvent){
            is RequestEvent.AddRequest -> {
                viewModelScope.launch {
                    addRequestUseCase.execute(requestEvent.model).collect{
                        _requestId.value = LastIdState.Id(it.id)
                    }

                }
            }
            is RequestEvent.DeleteRequest -> {
                viewModelScope.launch{
                    if(LastIdState.Id(requestEvent.id) == _requestId.value){
                        _requestId.value = LastIdState.Null
                    }
                    deleteRequestUseCase.execute(requestEvent.id)
                }
            }
            is RequestEvent.UpdateRequest -> {
                viewModelScope.launch{
                    updateRequestUseCase.execute(requestEvent.requestModel)
                }
            }
            RequestEvent.SaveLastId -> {
                viewModelScope.launch{
                    saveLastRequestIdUseCase.execute(_requestId.value)
                }
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