package com.yas.requests.models


import kotlinx.serialization.Serializable


@Serializable
internal data class KeyValueDTO(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValueDTO {
            return KeyValueDTO("", "")
        }
    }
}
