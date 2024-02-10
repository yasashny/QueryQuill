package ru.yasdev.data.requestsDb

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.yasdev.data.requestsDb.models.Request

@Database(
    entities = [Request::class],
    version = 1
)
abstract class RequestDataBase: RoomDatabase() {

    abstract val dao: RequestDao
}