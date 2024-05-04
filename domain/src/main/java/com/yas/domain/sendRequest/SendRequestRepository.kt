package com.yas.domain.sendRequest

interface SendRequestRepository {
    suspend fun sendRequest(model: SendRequestModel): ResponseModel
}