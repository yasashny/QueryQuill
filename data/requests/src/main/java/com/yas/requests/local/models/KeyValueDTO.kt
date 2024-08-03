package com.yas.requests.local.models


import kotlinx.serialization.Serializable


@Serializable
data class KeyValueDTO(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValueDTO {
            return KeyValueDTO("", "")
        }
    }
}
