package ru.yasdev.data.sendRequest

import ru.yasdev.domain.sendRequest.SendRequestModel
import ru.yasdev.domain.sendRequest.SendRequestRepository

class SendRequestRepositoryImpl(private val dataSource: SendRequestDataSource): SendRequestRepository {
    override suspend fun sendRequest(model: SendRequestModel) {
        dataSource.sendRequest(model)
    }
}