package org.queryquill.app.core.database.models


import kotlinx.serialization.Serializable


@Serializable
internal data class KeyValueDBO(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValueDBO {
            return KeyValueDBO("", "")
        }
    }
}
