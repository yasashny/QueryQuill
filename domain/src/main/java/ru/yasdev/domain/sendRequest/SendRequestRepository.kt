package ru.yasdev.domain.sendRequest

interface SendRequestRepository {
    suspend fun sendRequest(model: SendRequestModel)
}