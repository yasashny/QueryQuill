package com.yas.requests_data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yas.requests_data.local.converters.Converters
import com.yas.requests_data.local.dao.RequestDao
import com.yas.requests_data.local.dao.ResponseDao
import com.yas.requests_data.local.models.RequestDBO
import com.yas.requests_data.local.models.ResponseDBO

@Database(
    entities = [RequestDBO::class, ResponseDBO::class], version = 1
)
@TypeConverters(Converters::class)
internal abstract class RequestsDataBase : RoomDatabase() {

    abstract val requestDao: RequestDao
    abstract val responseDao: ResponseDao
}