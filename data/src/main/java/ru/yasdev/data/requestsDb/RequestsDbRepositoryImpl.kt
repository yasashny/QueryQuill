package ru.yasdev.data.requestsDb

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

class RequestsDbRepositoryImpl: RequestsDbRepository {
    override fun getListOfRequests(): Flow<List<RequestsListItemModel>> {
        TODO("Not yet implemented")
    }

    override fun getRequest(): Flow<RequestModel> {
        TODO("Not yet implemented")
    }

    override fun addRequest(model: AddRequestModel) {
        TODO("Not yet implemented")
    }

    override fun updateRequest(model: RequestModel) {
        TODO("Not yet implemented")
    }

    override fun deleteRequest(id: Int) {
        TODO("Not yet implemented")
    }
}