package com.yas.domain.requestsDb.states

// For body screen


enum class TextType(val title: String) : BasicState {
    JSON("Json") {
        override fun isDefault(): Boolean {
            return true
        }
    },

    XML("Xml") {
        override fun isDefault(): Boolean {
            return true
        }
    },

    PLAIN("Plain") {
        override fun isDefault(): Boolean {
            return true
        }
    },

    OTHER("Other") {
        override fun isDefault(): Boolean {
            return true
        }
    }
}