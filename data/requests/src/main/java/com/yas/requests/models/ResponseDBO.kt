package com.yas.requests.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yas.model.ContentType

@Entity
internal data class ResponseDBO(
    @PrimaryKey val id: Long,
    val status: String,
    val fileName: String,
    val contentLength: String,
    val time: String,
    val contentType: ContentType,
    val headers: List<KeyValueDTO>
)
