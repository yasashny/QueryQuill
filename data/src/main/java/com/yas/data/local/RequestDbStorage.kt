package com.yas.data.local

import android.content.Context
import androidx.room.Room
import com.yas.data.local.models.ResponseEntity
import com.yas.data.mappers.toRequestEntity
import com.yas.data.mappers.toRequestModel
import com.yas.data.mappers.toResponseEntity
import com.yas.data.mappers.toResponseModel
import com.yas.domain.requestsDb.models.AddRequestModel
import com.yas.domain.requestsDb.models.RequestsListItemModel
import com.yas.domain.sendRequest.RequestResponseModel
import kotlinx.coroutines.flow.Flow

class RequestDbStorage(context: Context) {

    private val db = Room.databaseBuilder(
        context, DataBase::class.java, "request.db"
    ).build()


    fun getListOfRequests(): Flow<List<RequestsListItemModel>> {
        return db.requestDao.getListOfRequests()
    }

    suspend fun getRequest(id: Int): RequestResponseModel {
        val requestEntity = db.requestDao.getRequest(id)
        val responseEntity = db.responseDao.getResponse(id)
        return RequestResponseModel(
            requestEntity.toRequestModel(), responseEntity.toResponseModel()
        )
    }

    suspend fun addRequest(model: AddRequestModel): RequestResponseModel {
        val id = db.requestDao.insertRequest(
            model.toRequestEntity()
        )
        db.responseDao.insertResponse(
            ResponseEntity(
                id = id.toInt(),
                status = "--",
                body = byteArrayOf(),
                contentLength = "--",
                time = "--",
                contentType = null,
                contentSubtype = null,
                headers = emptyList()
            )
        )
        return getRequest(id.toInt())

    }

    suspend fun updateRequest(model: RequestResponseModel) {
        db.requestDao.insertRequest(
            model.request.toRequestEntity()
        )
        db.responseDao.insertResponse(model.response.toResponseEntity(model.request.id))
    }

    suspend fun deleteRequest(id: Int) {
        db.requestDao.deleteRequest(id)
        db.responseDao.deleteResponse(id)
    }
}