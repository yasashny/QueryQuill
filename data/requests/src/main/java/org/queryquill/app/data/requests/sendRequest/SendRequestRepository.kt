package org.queryquill.app.data.requests.sendRequest

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.data.requests.local.dataSource.ResponseLocalDataSource
import org.queryquill.app.data.requests.mappers.toDBO
import org.queryquill.app.data.requests.mappers.toDTO

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