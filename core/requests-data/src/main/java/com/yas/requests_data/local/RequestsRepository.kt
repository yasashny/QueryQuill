package com.yas.requests_data.local

import com.yas.requests_data.local.mappers.toDBO
import com.yas.requests_data.local.mappers.toDTO
import com.yas.requests_data.local.models.AddRequestModelDTO
import com.yas.requests_data.local.models.RequestDTO
import kotlinx.coroutines.flow.Flow
import com.yas.requests_data.local.models.RequestsListItemDTO
import com.yas.requests_data.local.models.ResponseDBO
import com.yas.requests_data.local.models.ResponseDTO
import com.yas.requests_data.local.storage.CurrentRequestStorage
import com.yas.requests_data.local.storage.RequestsStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class RequestsRepository internal constructor(private val requestsStorage: RequestsStorage, private val currentRequestStorage: CurrentRequestStorage){

    fun getListOfRequests(): Flow<List<RequestsListItemDTO>> {
        return requestsStorage.getListOfRequests()
    }

    fun getCurrentRequestOrNull(): Flow<RequestDTO?> {
        return currentRequestStorage.getId().map { value: Long? ->
            if(value != null){
                requestsStorage.getRequest(id = value).toDTO()
            }
            else{
                null
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentResponseOrNull(): Flow<ResponseDTO?> {
        return currentRequestStorage.getId().flatMapLatest { value: Long? ->
            if (value != null){
                requestsStorage.getResponse(id = value).map {responseDBO: ResponseDBO ->
                    responseDBO.toDTO()
                }
            }
            else{
                flowOf(null)
            }
        }
    }

    suspend fun changeCurrentRequestId(requestId: Long?) {
        currentRequestStorage.saveId(requestId)
    }



    suspend fun addRequest(model: AddRequestModelDTO) {
        val id = requestsStorage.add(model)
        currentRequestStorage.saveId(id)
    }

    suspend fun updateRequest(model: RequestDTO) {
        requestsStorage.updateRequest(model.toDBO())
    }


    suspend fun deleteRequest(id: Long) {
        requestsStorage.delete(id)
        currentRequestStorage.saveId(null)
    }
}