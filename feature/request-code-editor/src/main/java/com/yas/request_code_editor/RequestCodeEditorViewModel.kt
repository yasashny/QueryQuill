package com.yas.request_code_editor

import androidx.lifecycle.ViewModel
import com.yas.requests.local.TransactionsRepository
import java.net.URI

internal class RequestCodeEditorViewModel(private val repository: TransactionsRepository) :
    ViewModel() {

    fun getTextFileUri(textFileName: String): URI {
        return repository.getFileUriByName(textFileName)
    }

}