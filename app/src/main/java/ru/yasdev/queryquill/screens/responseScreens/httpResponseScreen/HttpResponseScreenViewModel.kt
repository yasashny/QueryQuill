package ru.yasdev.queryquill.screens.responseScreens.httpResponseScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HttpResponseScreenViewModel : ViewModel() {

    private val _counter = MutableStateFlow("")
    val counter = _counter.asStateFlow()

    fun incrementCounter(text: String) {
        _counter.value = text
    }
}