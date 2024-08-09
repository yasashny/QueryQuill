package com.yas.model


data class KeyValue(val key: String, val value: String) {
    companion object {
        fun empty(): KeyValue = KeyValue("", "")
    }
}
