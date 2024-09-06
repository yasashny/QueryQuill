package org.queryquill.app.data.requests.sendRequest

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.data.requests.local.TransactionsRepository
import org.queryquill.app.data.requests.local.dataSource.ResponseLocalDataSource
import org.queryquill.app.data.requests.mappers.toDBO
import org.queryquill.app.data.requests.mappers.toDTO
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
            if (model.bodyState::class == BodyState.Text::class) {
                requestModelUri =
                    repository.getFileUriByName((model.bodyState as BodyState.Text).textFileName)
            }
            dataSource.sendRequest(model.toDTO(), requestModelUri).let { responseDTO ->
                responseLocalDataSource.update(responseDTO.toDBO(model.id))
            }
        }
    }
}