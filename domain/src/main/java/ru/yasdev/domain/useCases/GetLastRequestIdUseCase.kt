package ru.yasdev.domain.useCases

import kotlinx.coroutines.flow.Flow
import ru.yasdev.domain.lastRequest.LastRequestRepository
import ru.yasdev.domain.utils.RequestState

class GetLastRequestIdUseCase(private val repository: LastRequestRepository) {

    fun execute(): Flow<Int?> {
        return repository.getLastRequestId()
    }

}