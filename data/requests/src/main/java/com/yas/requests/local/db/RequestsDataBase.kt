package com.yas.requests.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yas.requests.local.converters.Converters
import com.yas.requests.local.dao.RequestDao
import com.yas.requests.local.dao.ResponseDao
import com.yas.requests.local.dao.TransactionDao
import com.yas.requests.models.RequestDBO
import com.yas.requests.models.ResponseDBO
import com.yas.requests.models.TransactionDBO

@Database(
    entities = [RequestDBO::class, ResponseDBO::class, TransactionDBO::class], version = 1
)
@TypeConverters(Converters::class)
internal abstract class RequestsDataBase : RoomDatabase() {

    abstract val requestDao: RequestDao
    abstract val responseDao: ResponseDao
    abstract val transactionDao: TransactionDao
}