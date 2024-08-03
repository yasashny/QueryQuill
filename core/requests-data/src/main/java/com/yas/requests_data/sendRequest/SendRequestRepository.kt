package com.yas.requests_data.sendRequest

import com.yas.requests_data.local.mappers.toDBO
import com.yas.requests_data.local.models.RequestDTO
import com.yas.requests_data.local.models.ResponseDTO
import com.yas.requests_data.local.storage.CurrentRequestStorage
import com.yas.requests_data.local.storage.RequestsStorage
import kotlinx.coroutines.flow.first

class SendRequestRepository internal constructor(private val dataSource: SendRequestDataSource, private val storage: RequestsStorage) {

    suspend fun sendRequest(model: RequestDTO) {
        dataSource.sendRequest(model).let {
            storage.updateResponse(it.toDBO(model.id))
        }
    }
}