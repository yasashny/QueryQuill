package ru.yasdev.data.requestsDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yasdev.data.requestsDb.models.RequestEntity

@Database(
    entities = [RequestEntity::class], version = 1
)
@TypeConverters(Converters::class)
abstract class RequestDataBase : RoomDatabase() {

    abstract val dao: RequestDao
}