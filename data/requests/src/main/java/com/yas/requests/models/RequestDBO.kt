package com.yas.requests.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class RequestDBO(
    @PrimaryKey val id: Long,
    val bodyState: BodyStateDTO,
    val header: List<KeyValueDTO>,
    val query: List<KeyValueDTO>,
    val authState: AuthStateDTO,
    val type: HttpTypeDTO,
    val url: String
)
