package ru.yasdev.data.requestsDb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yasdev.domain.requestsDb.models.AuthState
import ru.yasdev.domain.requestsDb.models.BodyState
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.KeyValue

@Entity
data class RequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val bodyState: BodyState,
    val header: List<KeyValue>,
    val query: List<KeyValue>,
    val authState: AuthState,
    val type: HttpType,
    val url: String
)
