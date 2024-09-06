package org.queryquill.app.core.model


data class KeyValue(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValue = KeyValue("", "")
    }
}
