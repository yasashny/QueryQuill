package ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.sendRequest.SendRequestUseCase

class HttpResponseScreenViewModel(
    private val sendRequestUseCase: SendRequestUseCase
) : ViewModel() {

    private val _counter = MutableStateFlow("")
    val counter = _counter.asStateFlow()

    fun incrementCounter(text: String) {
        _counter.value = text
    }
    fun sendRequest(requestModel: RequestModel){
        viewModelScope.launch {
            sendRequestUseCase.execute(requestModel)
        }
    }
}