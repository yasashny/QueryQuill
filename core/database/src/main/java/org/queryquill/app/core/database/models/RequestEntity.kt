package org.queryquill.app.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class RequestEntity(
    @PrimaryKey val id: Long,
    val bodyState: BodyStateDBO,
    val header: List<KeyValueDBO>,
    val query: List<KeyValueDBO>,
    val authState: AuthStateDBO,
    val type: HttpTypeDBO,
    val url: String
)
