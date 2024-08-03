package com.yas.requests_data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class ResponseDBO(
    @PrimaryKey val id: Long,
    val status: String,
    val body: ByteArray,
    val contentLength: String,
    val time: String,
    val contentType: String?,
    val contentSubtype: String?,
    val headers: List<KeyValueDTO>
)
