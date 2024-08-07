package com.yas.model

import androidx.compose.runtime.Immutable

@Immutable
data class RequestModel(
    val id: Long,
    val bodyState: BodyState,
    val header: ImmutableList,
    val query: ImmutableList,
    val auth: AuthState,
    val type: HttpType,
    val url: String
) {
    companion object{
        fun default(): RequestModel {
            return RequestModel(
                id = -1,
                bodyState = BodyState.NoBody,
                header = ImmutableList(emptyList()),
                query = ImmutableList(emptyList()),
                type = HttpType.GET,
                url = "",
                auth = AuthState.NoAuth
            )
        }
    }

}


