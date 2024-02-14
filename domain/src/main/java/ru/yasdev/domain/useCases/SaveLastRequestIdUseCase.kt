package ru.yasdev.domain.useCases

import ru.yasdev.domain.lastRequest.LastRequestRepository
import ru.yasdev.domain.utils.RequestState

class SaveLastRequestIdUseCase(private val repository: LastRequestRepository) {
    suspend fun execute(id: Int?){
        repository.saveLastRequestId(id)
    }

}