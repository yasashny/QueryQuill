package com.yas.requests.sendRequest

import com.yas.requests.local.mappers.toDBO
import com.yas.requests.local.models.RequestDTO
import com.yas.requests.local.storage.RequestsStorage

class SendRequestRepository internal constructor(private val dataSource: SendRequestDataSource, private val storage: RequestsStorage) {

    suspend fun sendRequest(model: RequestDTO) {
        dataSource.sendRequest(model).let {
            storage.updateResponse(it.toDBO(model.id))
        }
    }
}