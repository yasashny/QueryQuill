package com.yas.requests.local.dataSource

import android.content.Context
import androidx.room.Room
import com.yas.requests.local.db.RequestsDataBase
import com.yas.requests.models.ResponseDBO
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.net.URI

internal class ResponseLocalDataSource(private val context: Context) {

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
                contentType = null,
                contentSubtype = null,
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
    fun getResponseTextFileUri(textFileName: String): URI {
        val file = File(context.filesDir, textFileName)
        if (!file.exists()) {
            file.writeText("")
        }
        return file.toURI()
    }
}