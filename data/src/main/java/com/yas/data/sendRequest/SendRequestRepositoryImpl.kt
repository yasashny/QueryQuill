package com.yas.data.sendRequest

import com.yas.domain.sendRequest.ResponseModel
import com.yas.domain.sendRequest.SendRequestModel
import com.yas.domain.sendRequest.SendRequestRepository

class SendRequestRepositoryImpl(private val dataSource: SendRequestDataSource): SendRequestRepository {
    override suspend fun sendRequest(model: SendRequestModel): ResponseModel {
        return dataSource.sendRequest(model)
    }
}