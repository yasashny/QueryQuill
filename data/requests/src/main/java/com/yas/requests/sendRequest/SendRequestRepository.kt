package com.yas.requests.sendRequest

import com.yas.model.RequestModel
import com.yas.requests.local.dataSource.ResponseLocalDataSource
import com.yas.requests.mappers.toDBO
import com.yas.requests.mappers.toDTO

class SendRequestRepository internal constructor(
    private val dataSource: SendRequestLocalDataSource,
    private val responseLocalDataSource: ResponseLocalDataSource
) {

    suspend fun sendRequest(model: RequestModel) {
        dataSource.sendRequest(model.toDTO()).let { responseDTO ->
            responseLocalDataSource.update(responseDTO.toDBO(model.id))
        }
    }
}