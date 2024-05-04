package com.yas.domain.requestsDb.useCases

import kotlinx.coroutines.flow.Flow
import com.yas.domain.requestsDb.models.RequestsListItemModel
import com.yas.domain.requestsDb.repositories.LocalDbRepository

class GetListOfRequestsUseCase(private val localDbRepository: LocalDbRepository) {


    fun execute(): Flow<List<RequestsListItemModel>> {
        return localDbRepository.getListOfRequests()
    }

}