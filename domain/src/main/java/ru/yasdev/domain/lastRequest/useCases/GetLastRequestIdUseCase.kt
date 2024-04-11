package ru.yasdev.domain.lastRequest.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.lastRequest.repositories.LastRequestRepository

class GetLastRequestIdUseCase(private val repository: LastRequestRepository) {

    fun execute(): Flow<Int?> {
        return repository.getLastRequestId()
    }

}