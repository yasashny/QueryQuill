package ru.yasdev.data.local

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import ru.yasdev.data.local.models.ResponseEntity
import ru.yasdev.data.mappers.toRequestEntity
import ru.yasdev.data.mappers.toRequestModel
import ru.yasdev.data.mappers.toResponseEntity
import ru.yasdev.data.mappers.toResponseModel
import ru.yasdev.domain.requestsDb.models.AddRequestModel
import ru.yasdev.domain.requestsDb.models.RequestModel
import ru.yasdev.domain.requestsDb.models.RequestsListItemModel
import ru.yasdev.domain.sendRequest.RequestResponseModel

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
        return RequestResponseModel(requestEntity.toRequestModel(), responseEntity.toResponseModel())
    }

    suspend fun addRequest(model: AddRequestModel): RequestResponseModel {
        val id = db.requestDao.insertRequest(
            model.toRequestEntity()
        )
        db.responseDao.insertResponse(ResponseEntity(
            id = id.toInt(),
            status = "--",
            body = "--",
            contentLength = "--",
            time = "--"
        ))
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