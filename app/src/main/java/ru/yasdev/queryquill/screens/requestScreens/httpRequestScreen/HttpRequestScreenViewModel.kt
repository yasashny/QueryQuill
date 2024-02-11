package ru.yasdev.queryquill.screens.requestScreens.httpRequestScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HttpRequestScreenViewModel: ViewModel() {

    private val _counter = MutableStateFlow("")
    val counter = _counter.asStateFlow()

    fun incrementCounter(text: String) {
        _counter.value = text
    }
}