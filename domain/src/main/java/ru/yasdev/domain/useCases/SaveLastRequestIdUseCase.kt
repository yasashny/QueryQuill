package ru.yasdev.domain.useCases

import ru.yasdev.domain.lastRequest.LastRequestRepository
import ru.yasdev.domain.utils.LastIdState

class SaveLastRequestIdUseCase(private val repository: LastRequestRepository) {
    suspend fun execute(lastIdState: LastIdState){
        repository.saveLastRequestId(lastIdState)
    }

}