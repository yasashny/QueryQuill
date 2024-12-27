package org.queryquill.app.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.queryquill.app.core.model.ContentType

@Entity
internal data class ResponseEntity(
    @PrimaryKey val id: Long,
    val status: String,
    val fileName: String,
    val contentLength: String,
    val time: String,
    val contentType: ContentType,
    val headers: List<KeyValueDBO>
)
