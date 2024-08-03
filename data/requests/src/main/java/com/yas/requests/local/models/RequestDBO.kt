package com.yas.requests.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class RequestDBO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val label: String,
    val bodyState: BodyStateDTO,
    val header: List<KeyValueDTO>,
    val query: List<KeyValueDTO>,
    val authState: AuthStateDTO,
    val type: HttpTypeDTO,
    val url: String
)
