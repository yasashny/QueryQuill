package org.queryquill.app.data.requests.local.dataSource

import android.content.Context
import androidx.room.Room
import org.queryquill.app.data.requests.local.db.RequestsDataBase
import org.queryquill.app.data.requests.models.AuthStateDTO
import org.queryquill.app.data.requests.models.BodyStateDTO
import org.queryquill.app.data.requests.models.HttpTypeDTO
import org.queryquill.app.data.requests.models.KeyValueDTO
import org.queryquill.app.data.requests.models.RequestDBO

internal class RequestLocalDataSource(context: Context) {

    private val db = Room.databaseBuilder(
        context, RequestsDataBase::class.java, "request.db"
    ).build()

    suspend fun create(id: Long) {
        db.requestDao.insertRequest(
            RequestDBO(
                id = id,
                bodyState = BodyStateDTO.NoBody,
                header = listOf(KeyValueDTO.empty()),
                query = listOf(KeyValueDTO.empty()),
                type = HttpTypeDTO.GET,
                url = "",
                authState = AuthStateDTO.NoAuth
            )
        )
    }

    suspend fun read(id: Long): RequestDBO? {
        return db.requestDao.getRequest(id)
    }

    suspend fun update(model: RequestDBO) {
        db.requestDao.insertRequest(
            model
        )
    }

    suspend fun delete(id: Long) {
        db.requestDao.deleteRequest(id)
    }
}