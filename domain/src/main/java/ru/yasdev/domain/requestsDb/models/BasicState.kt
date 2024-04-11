package ru.yasdev.domain.requestsDb.models

interface BasicState{
    val name: String
    fun isDefault(): Boolean
}