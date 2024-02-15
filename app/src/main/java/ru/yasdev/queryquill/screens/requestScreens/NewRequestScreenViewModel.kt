package ru.yasdev.queryquill.screens.requestScreens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.queryquill.activity.MainActivityViewModel
import ru.yasdev.queryquill.activity.RequestEvent

class NewRequestScreenViewModel: ViewModel() {

    private val _label = MutableStateFlow("MyRequest")
    val label = _label.asStateFlow()

    fun changeNewRequestLabel(label: String){
        _label.value = label
    }

    fun addRequest(viewModel: MainActivityViewModel){
        viewModel.onEvent(RequestEvent.AddRequest(AddRequestModel(label.value)))
    }
}