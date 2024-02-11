package ru.yasdev.domain.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.lastRequest.LastRequestRepository

class GetLastRequestIdUseCase(private val repository: LastRequestRepository) {

    fun execute(): Flow<Int?> {
        return repository.getLastRequestId()
    }

}