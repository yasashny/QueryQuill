package com.yas.model

data class SendRequestModel(
    val bodyState: BodyState,
    val query: List<com.yas.model.KeyValue>,
    val headers: List<com.yas.model.KeyValue>,
    val auth: AuthState,
    val url: String,
    val type: HttpType
)
