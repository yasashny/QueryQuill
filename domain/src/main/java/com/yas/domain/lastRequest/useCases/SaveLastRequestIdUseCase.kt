package com.yas.domain.lastRequest.useCases

import com.yas.domain.lastRequest.repositories.LastRequestRepository

class SaveLastRequestIdUseCase(private val repository: LastRequestRepository) {
    suspend fun execute(id: Int?) {
        repository.saveLastRequestId(id)
    }

}