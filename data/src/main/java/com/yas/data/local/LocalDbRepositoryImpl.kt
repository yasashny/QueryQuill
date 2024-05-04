package com.yas.data.local

import kotlinx.coroutines.flow.Flow
import com.yas.domain.requestsDb.models.AddRequestModel
import com.yas.domain.requestsDb.models.RequestsListItemModel
import com.yas.domain.requestsDb.repositories.LocalDbRepository
import com.yas.domain.sendRequest.RequestResponseModel

class LocalDbRepositoryImpl(private val requestStorage: RequestDbStorage) :
    LocalDbRepository {
    override fun getListOfRequests(): Flow<List<RequestsListItemModel>> {
        return requestStorage.getListOfRequests()
    }

    override suspend fun getRequest(id: Int): RequestResponseModel {
        return requestStorage.getRequest(id)
    }

    override suspend fun addRequest(model: AddRequestModel): RequestResponseModel {
        return requestStorage.addRequest(model)
    }

    override suspend fun updateRequest(model: RequestResponseModel) {
        requestStorage.updateRequest(model)
    }

    override suspend fun deleteRequest(id: Int) {
        requestStorage.deleteRequest(id)
    }
}