package ru.yasdev.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResponseEntity(
    @PrimaryKey val id: Int,
    val status: String,
    val body: String,
    val contentLength: String,
    val time: String
)
