package com.yas.domain.requestsDb.models

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class KeyValue(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValue = KeyValue("", "")
    }
}
