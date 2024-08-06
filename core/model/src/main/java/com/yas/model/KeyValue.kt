package com.yas.model

import androidx.compose.runtime.Immutable

@Immutable
data class KeyValue(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValue = KeyValue("", "")
    }
}
