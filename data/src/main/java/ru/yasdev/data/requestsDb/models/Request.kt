package ru.yasdev.data.requestsDb.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Request(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val label: String,
    val test: String
)
