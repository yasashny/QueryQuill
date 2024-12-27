package org.queryquill.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.queryquill.app.core.database.utils.Converters
import org.queryquill.app.core.database.dao.RequestDao
import org.queryquill.app.core.database.dao.ResponseDao
import org.queryquill.app.core.database.dao.TransactionDao
import org.queryquill.app.core.database.models.RequestEntity
import org.queryquill.app.core.database.models.ResponseEntity
import org.queryquill.app.core.database.models.TransactionEntity

@Database(
    entities = [RequestEntity::class, ResponseEntity::class, TransactionEntity::class], version = 1
)
@TypeConverters(Converters::class)
internal abstract class QQDatabase : RoomDatabase() {

    abstract val requestDao: RequestDao
    abstract val responseDao: ResponseDao
    abstract val transactionDao: TransactionDao
}