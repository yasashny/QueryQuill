package ru.yasdev.data.requestsDb

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

class RequestsDbRepositoryImpl(private val requestsDbDataSource: RequestDbDataSource): RequestsDbRepository {
    override fun getListOfRequests(): Flow<List<RequestsListItemModel>> {
        return requestsDbDataSource.getListOfRequests()
    }

    override fun getRequest(id: Int): Flow<RequestModel> {
        return requestsDbDataSource.getRequest(id)
    }

    override suspend fun addRequest(model: AddRequestModel): Flow<RequestModel> {
        return requestsDbDataSource.addRequest(model)
    }

    override suspend fun updateRequest(model: RequestModel) {
        requestsDbDataSource.updateRequest(model)
    }

    override suspend fun deleteRequest(id: Int) {
        requestsDbDataSource.deleteRequest(id)
    }
}