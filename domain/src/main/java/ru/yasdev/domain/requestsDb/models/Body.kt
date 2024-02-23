package ru.yasdev.domain.requestsDb.models

sealed interface Body {
    data class Text(val text: String) : Body
    data class Structured(val list: List<ListItem>) : Body
}