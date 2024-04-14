package ru.yasdev.domain.requestsDb.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel
import ru.yasdev.domain.requestsDb.repositories.LocalDbRepository

class GetListOfRequestsUseCase(private val localDbRepository: LocalDbRepository) {


    fun execute(): Flow<List<RequestsListItemModel>> {
        return localDbRepository.getListOfRequests()
    }

}