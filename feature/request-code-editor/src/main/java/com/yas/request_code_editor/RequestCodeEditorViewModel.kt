package com.yas.request_code_editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.BodyState
import com.yas.model.HttpType
import com.yas.model.RequestModel
import com.yas.model.TextType
import com.yas.requests.local.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RequestCodeEditorViewModel(private val repository: TransactionsRepository) : ViewModel() {

    private val _requestModel = MutableStateFlow<RequestModel?>(RequestModel.default())
    val requestModel = _requestModel.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentRequestOrNull().collect { value ->
                _requestModel.value = value
            }
        }
    }

    val text = mutableStateOf("")

    fun update(t: String){
        text.value = t
    }

//    fun updateBody(text: String) {
//        when (val model = requestModel.value) {
//            null -> {}
//            else -> {
//                when (val body = model.bodyState) {
//                    is BodyState.BinaryFile -> {}
//                    is BodyState.FormUrlEncoded -> {}
//                    is BodyState.MultipartForm -> {}
//                    BodyState.NoBody -> {}
//                    is BodyState.Text -> {
//                        _requestModel.value = model.copy(
//                            bodyState = BodyState.Text(
//                                text = text,
//                                textType = body.textType
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }


    fun saveBody() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val model = _requestModel.value) {
                null -> {}
                else -> {
                    repository.updateRequest(model.copy(bodyState = BodyState.Text(text.value, TextType.PLAIN)))
                }
            }
        }
    }
}