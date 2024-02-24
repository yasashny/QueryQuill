package ru.yasdev.domain.requestsDb.models

import androidx.compose.runtime.Immutable

@Immutable
sealed interface Body {
    @Immutable
    data class Text(val text: String) : Body
    @Immutable
    data class Structured(val list: List<ListItem>) : Body
}