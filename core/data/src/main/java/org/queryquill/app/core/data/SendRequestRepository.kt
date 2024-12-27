package org.queryquill.app.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.queryquill.app.core.database.ResponseDataSource
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.network.SendRequestDataSource

class SendRequestRepository internal constructor(
    private val sendRequestDataSource: SendRequestDataSource,
    private val responseDataSource: ResponseDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun sendRequest(model: RequestModel) {
        withContext(ioDispatcher) {
            sendRequestDataSource.sendRequest(model).let { response ->
                responseDataSource.update(response)
            }
        }
    }
}