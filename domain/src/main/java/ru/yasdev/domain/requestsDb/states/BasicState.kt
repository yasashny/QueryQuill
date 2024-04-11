package ru.yasdev.domain.requestsDb.states

interface BasicState {
    val name: String
    fun isDefault(): Boolean
}