package ru.yasdev.data.local

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel
import ru.yasdev.domain.requestsDb.repositories.LocalDbRepository
import ru.yasdev.domain.sendRequest.RequestResponseModel
import ru.yasdev.domain.sendRequest.ResponseModel

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