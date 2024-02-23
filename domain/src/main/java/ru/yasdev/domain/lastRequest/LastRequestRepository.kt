package ru.yasdev.domain.lastRequest

import kotlinx.coroutines.flow.Flow

interface LastRequestRepository {

    fun getLastRequestId(): Flow<Int?>
    suspend fun saveLastRequestId(id: Int?)

}