package com.yas.new_request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.NewRequestModel
import com.yas.requests.local.RequestsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class NewRequestViewModel(private val repository: RequestsRepository) : ViewModel() {

    private val _newRequestModel = MutableStateFlow(NewRequestModel(""))
    val newRequestModel = _newRequestModel.asStateFlow()

    fun addNewRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRequest(newRequestModel.value)
        }
    }

    fun onEvent(event: UpdateNewRequestModel) {
        when (event) {
            is UpdateNewRequestModel.UpdateLabel -> {
                _newRequestModel.value = newRequestModel.value.copy(label = event.label)
            }
        }
    }
}