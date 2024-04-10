package ru.yasdev.domain.requestsDb.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ListItem(val name: String, val value: String)
