package ru.yasdev.domain.requestsDb.repositories

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel
import ru.yasdev.domain.sendRequest.RequestResponseModel
import ru.yasdev.domain.sendRequest.ResponseModel

interface LocalDbRepository {

    fun getListOfRequests(): Flow<List<RequestsListItemModel>>
    suspend fun getRequest(id: Int): RequestResponseModel
    suspend fun addRequest(model: AddRequestModel): RequestResponseModel
    suspend fun updateRequest(model: RequestResponseModel)
    suspend fun deleteRequest(id: Int)

}