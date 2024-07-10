package com.yas.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yas.domain.requestsDb.models.KeyValue

@Entity
data class ResponseEntity(
    @PrimaryKey val id: Int,
    val status: String,
    val body: ByteArray,
    val contentLength: String,
    val time: String,
    val contentType: String?,
    val contentSubtype: String?,
    val headers: List<KeyValue>
)
