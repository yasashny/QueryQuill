package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.yasdev.domain.requestsDb.models.RequestModel

class HttpRequestScreenViewModel(val requestModel: StateFlow<RequestModel>): ViewModel() {

    private val _counter = MutableStateFlow("")
    val counter = _counter.asStateFlow()

    fun incrementCounter(text: String) {
        _counter.value = text
    }
}