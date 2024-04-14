package ru.yasdev.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yasdev.data.local.models.RequestEntity
import ru.yasdev.data.local.models.ResponseEntity

@Database(
    entities = [RequestEntity::class, ResponseEntity::class], version = 1
)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {

    abstract val requestDao: RequestDao
    abstract val responseDao: ResponseDao
}