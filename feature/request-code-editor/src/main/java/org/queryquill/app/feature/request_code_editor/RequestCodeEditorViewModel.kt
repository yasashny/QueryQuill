package org.queryquill.app.feature.request_code_editor

import androidx.lifecycle.ViewModel
import org.queryquill.app.data.requests.local.TransactionsRepository
import java.net.URI

internal class RequestCodeEditorViewModel(private val repository: TransactionsRepository) :
    ViewModel() {

    fun getTextFileUri(textFileName: String): URI {
        return repository.getFileUriByName(textFileName)
    }

}