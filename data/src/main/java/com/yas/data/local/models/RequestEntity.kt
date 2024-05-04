package com.yas.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yas.domain.requestsDb.models.HttpType
import com.yas.domain.requestsDb.models.KeyValue
import com.yas.domain.requestsDb.states.AuthState
import com.yas.domain.requestsDb.states.BodyState

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
