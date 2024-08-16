package com.yas.requests.sendRequest

import com.yas.model.BodyState
import com.yas.model.RequestModel
import com.yas.requests.local.TransactionsRepository
import com.yas.requests.local.dataSource.RequestLocalDataSource
import com.yas.requests.local.dataSource.ResponseLocalDataSource
import com.yas.requests.mappers.toDBO
import com.yas.requests.mappers.toDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.URI

class SendRequestRepository internal constructor(
    private val dataSource: SendRequestLocalDataSource,
    private val responseLocalDataSource: ResponseLocalDataSource,
    private val repository: TransactionsRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun sendRequest(model: RequestModel) {
        withContext(ioDispatcher) {
            var requestModelUri: URI? = null
            if (model.bodyState::class == BodyState.Text::class){
                requestModelUri = repository.getFileUriByName((model.bodyState as BodyState.Text).textFileName)
            }
            dataSource.sendRequest(model.toDTO(), requestModelUri).let { responseDTO ->
                responseLocalDataSource.update(responseDTO.toDBO(model.id))
            }
        }
    }
}