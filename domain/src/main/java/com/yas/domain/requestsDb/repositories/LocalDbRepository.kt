package com.yas.domain.requestsDb.repositories

import kotlinx.coroutines.flow.Flow
import com.yas.domain.requestsDb.models.AddRequestModel
import com.yas.domain.requestsDb.models.RequestsListItemModel
import com.yas.domain.sendRequest.RequestResponseModel

interface LocalDbRepository {

    fun getListOfRequests(): Flow<List<RequestsListItemModel>>
    suspend fun getRequest(id: Int): RequestResponseModel
    suspend fun addRequest(model: AddRequestModel): RequestResponseModel
    suspend fun updateRequest(model: RequestResponseModel)
    suspend fun deleteRequest(id: Int)

}