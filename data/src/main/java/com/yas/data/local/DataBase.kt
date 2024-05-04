package com.yas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yas.data.local.models.RequestEntity
import com.yas.data.local.models.ResponseEntity

@Database(
    entities = [RequestEntity::class, ResponseEntity::class], version = 1
)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {

    abstract val requestDao: RequestDao
    abstract val responseDao: ResponseDao
}