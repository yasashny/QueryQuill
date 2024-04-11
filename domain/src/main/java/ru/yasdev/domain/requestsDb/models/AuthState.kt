package ru.yasdev.domain.requestsDb.models

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthState {
    val name: String
    @Serializable
    data object NoAuth : AuthState{
        override val name: String
            get() = "No Auth"
    }

    @Serializable
    data class Basic(val userName: String, val password: String) : AuthState{
        override val name: String
            get() = "Basic"
        companion object{
            fun default(): Basic{
                return Basic("", "")
            }
        }
    }

    fun isDefault(): Boolean {
        return when (this) {
            is Basic -> this == Basic.default()
            NoAuth -> true
        }
    }

}