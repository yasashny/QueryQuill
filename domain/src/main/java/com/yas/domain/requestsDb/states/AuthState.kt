package com.yas.domain.requestsDb.states




sealed interface AuthState : BasicState {
    override val name: String


    data object NoAuth : AuthState {
        override val name: String
            get() = "No Auth"
    }


    data class Basic(val userName: String, val password: String) : AuthState {
        override val name: String
            get() = "Basic"

        companion object {
            fun default(): Basic {
                return Basic("", "")
            }
        }
    }

    override fun isDefault(): Boolean {
        return when (this) {
            is Basic -> this == Basic.default()
            NoAuth -> true
        }
    }
}