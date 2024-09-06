package org.queryquill.app.data.requests.models


import kotlinx.serialization.Serializable


@Serializable
internal data class KeyValueDTO(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValueDTO {
            return KeyValueDTO("", "")
        }
    }
}
