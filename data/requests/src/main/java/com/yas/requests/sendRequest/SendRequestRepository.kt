package com.yas.requests.sendRequest

import com.yas.model.RequestModel
import com.yas.requests.local.dataSource.ResponseLocalDataSource
import com.yas.requests.mappers.toDBO
import com.yas.requests.mappers.toDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SendRequestRepository internal constructor(
    private val dataSource: SendRequestLocalDataSource,
    private val responseLocalDataSource: ResponseLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun sendRequest(model: RequestModel) {
        withContext(ioDispatcher) {
            dataSource.sendRequest(model.toDTO()).let { responseDTO ->
                responseLocalDataSource.update(responseDTO.toDBO(model.id))
            }
        }
    }
}