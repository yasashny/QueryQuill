package ru.yasdev.domain.requestsDb.models

import androidx.compose.runtime.Immutable

@Immutable
data class RequestModel(
    val id: Int,
    val label: String,
    val bodyState: BodyState,
    val header: ImmutableList,
    val query: ImmutableList,
    val auth: AuthState,
    val type: HttpType,
    val url: String
)

