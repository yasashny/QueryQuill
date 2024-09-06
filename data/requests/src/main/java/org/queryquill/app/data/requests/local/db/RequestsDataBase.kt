package org.queryquill.app.data.requests.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.queryquill.app.data.requests.local.converters.Converters
import org.queryquill.app.data.requests.local.dao.RequestDao
import org.queryquill.app.data.requests.local.dao.ResponseDao
import org.queryquill.app.data.requests.local.dao.TransactionDao
import org.queryquill.app.data.requests.models.RequestDBO
import org.queryquill.app.data.requests.models.ResponseDBO
import org.queryquill.app.data.requests.models.TransactionDBO

@Database(
    entities = [RequestDBO::class, ResponseDBO::class, TransactionDBO::class], version = 1
)
@TypeConverters(Converters::class)
internal abstract class RequestsDataBase : RoomDatabase() {

    abstract val requestDao: RequestDao
    abstract val responseDao: ResponseDao
    abstract val transactionDao: TransactionDao
}