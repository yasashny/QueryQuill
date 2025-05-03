package org.queryquill.app.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.queryquill.app.core.database.ResponseDataSource
import org.queryquill.app.core.datastore.CookieDataSource
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.network.SendRequestDataSource

class SendRequestRepository internal constructor(
    private val sendRequestDataSource: SendRequestDataSource,
    private val responseDataSource: ResponseDataSource,
    private val cookieDataSource: CookieDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun sendRequest(model: RequestModel) {
        withContext(ioDispatcher) {
            val cookie = cookieDataSource.getCookie().first()
            sendRequestDataSource.sendRequest(model, cookie).let { response ->
                responseDataSource.update(response)
                cookieDataSource.addNewCookie(response.headers.list.filter { keyValue -> keyValue.key == "Set-Cookie" }
                    .map { it.value })
            }
        }
    }
}