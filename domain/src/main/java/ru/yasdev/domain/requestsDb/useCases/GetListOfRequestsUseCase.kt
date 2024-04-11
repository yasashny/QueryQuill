package ru.yasdev.domain.requestsDb.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel
import ru.yasdev.domain.requestsDb.repositories.RequestsDbRepository

class GetListOfRequestsUseCase(private val requestsDbRepository: RequestsDbRepository) {


    fun execute(): Flow<List<RequestsListItemModel>> {
        return requestsDbRepository.getListOfRequests()
    }

}