package com.yas.domain.lastRequest.useCases

import kotlinx.coroutines.flow.Flow
import com.yas.domain.lastRequest.repositories.LastRequestRepository

class GetLastRequestIdUseCase(private val repository: LastRequestRepository) {

    fun execute(): Flow<Int?> {
        return repository.getLastRequestId()
    }

}