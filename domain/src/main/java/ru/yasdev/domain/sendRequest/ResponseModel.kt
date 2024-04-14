package ru.yasdev.domain.sendRequest

import kotlinx.serialization.Serializable

@Serializable
data class ResponseModel(val status: String, val body: String, val contentLength: String){
    companion object{
        fun default(): ResponseModel{
            return ResponseModel("--", "", "--")
        }
    }
}
