package ru.yasdev.domain.requestsDb

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

interface RequestsDbRepository {

    fun getListOfRequests(): Flow<List<RequestsListItemModel>>

    fun getRequest(): Flow<RequestModel>
    fun addRequest(model: AddRequestModel)
    fun updateRequest(model: RequestModel)
    fun deleteRequest(id: Int)

}