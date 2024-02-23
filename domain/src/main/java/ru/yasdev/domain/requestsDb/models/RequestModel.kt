package ru.yasdev.domain.requestsDb.models

data class RequestModel(
    val id: Int,
    val label: String,
    val body: Body,
    val header: List<ListItem>,
    val query: List<ListItem>,
    val type: HttpType,
    val url: String
)
