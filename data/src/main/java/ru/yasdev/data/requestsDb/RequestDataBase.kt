package ru.yasdev.data.requestsDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yasdev.data.requestsDb.models.DataRequestModel

@Database(
    entities = [DataRequestModel::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RequestDataBase : RoomDatabase() {

    abstract val dao: RequestDao
}