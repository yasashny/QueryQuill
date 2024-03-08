package ru.yasdev.domain.requestsDb.models

import androidx.compose.runtime.Immutable
@Immutable
data class RequestModel(
    val id: Int,
    val label: String,
    val body: Body,
    val header: ImmutableList,
    val query: ImmutableList,
    val auth: Auth,
    val type: HttpType,
    val url: String
)
@Immutable
data class ImmutableList(val list: List<ListItem>)
