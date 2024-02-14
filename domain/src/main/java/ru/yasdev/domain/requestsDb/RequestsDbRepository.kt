package ru.yasdev.domain.requestsDb

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

interface RequestsDbRepository {

    fun getListOfRequests(): Flow<List<RequestsListItemModel>>
    suspend fun getRequest(id: Int): RequestModel
    suspend fun addRequest(model: AddRequestModel): RequestModel
    suspend fun updateRequest(model: RequestModel)
    suspend fun deleteRequest(id: Int)

}