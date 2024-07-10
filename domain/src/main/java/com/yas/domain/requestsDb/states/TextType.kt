package com.yas.domain.requestsDb.states

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// For body screen

@Serializable
enum class TextType(val title: String) : BasicState {
    @SerialName("JSON")
    JSON("Json") {
        override fun isDefault(): Boolean {
            return true
        }
    },

    @SerialName("XML")
    XML("Xml") {
        override fun isDefault(): Boolean {
            return true
        }
    },

    @SerialName("PLAIN")
    PLAIN("Plain") {
        override fun isDefault(): Boolean {
            return true
        }
    },

    @SerialName("OTHER")
    OTHER("Other") {
        override fun isDefault(): Boolean {
            return true
        }
    }
}