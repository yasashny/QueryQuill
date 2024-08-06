package com.yas.requests.sendRequest

import com.yas.model.RequestModel
import com.yas.requests.mappers.toDBO
import com.yas.requests.mappers.toDTO
import com.yas.requests.local.RequestsLocalDataSource

class SendRequestRepository internal constructor(private val dataSource: SendRequestLocalDataSource, private val storage: RequestsLocalDataSource) {

    suspend fun sendRequest(model: RequestModel) {
        dataSource.sendRequest(model.toDTO()).let {
            storage.updateResponse(it.toDBO(model.id))
        }
    }
}