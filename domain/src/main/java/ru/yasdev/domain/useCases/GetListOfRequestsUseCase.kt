package ru.yasdev.domain.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.RequestsDbRepository
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel

class GetListOfRequestsUseCase(private val requestsDbRepository: RequestsDbRepository) {


    fun execute(): Flow<List<RequestsListItemModel>> {
        return requestsDbRepository.getListOfRequests()
    }

}