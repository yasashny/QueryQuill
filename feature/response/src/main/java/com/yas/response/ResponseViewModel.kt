package com.yas.response

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.ResponseModel
import com.yas.requests.local.TransactionsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.net.URI

internal class ResponseViewModel(private val transactionsRepository: TransactionsRepository) :
    ViewModel() {

    val responseModel = transactionsRepository.getCurrentResponseOrNull().map { responseOrNull ->
        responseOrNull ?: ResponseModel.default()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ResponseModel.default())

    fun getFileUriByName(textFileName: String): URI {
        return transactionsRepository.getFileUriByName(textFileName)
    }
}