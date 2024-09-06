package org.queryquill.app.data.requests.local.dataSource

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.data.requests.local.db.RequestsDataBase
import org.queryquill.app.data.requests.models.ResponseDBO

internal class ResponseLocalDataSource(context: Context) {

    private val db = Room.databaseBuilder(
        context, RequestsDataBase::class.java, "request.db"
    ).build()

    suspend fun create(id: Long) {
        db.responseDao.insertResponse(
            ResponseDBO(
                id = id,
                status = "--",
                fileName = "default.txt",
                contentLength = "--",
                time = "--",
                contentType = ContentType.Text.PLAIN,
                headers = emptyList()
            )
        )
    }

    fun read(id: Long): Flow<ResponseDBO?> {
        return db.responseDao.getResponse(id)
    }

    suspend fun update(model: ResponseDBO) {
        db.responseDao.insertResponse(model)
    }

    suspend fun delete(id: Long) {
        db.responseDao.deleteResponse(id)
    }
}