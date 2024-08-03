package com.yas.domain.requestsDb.models

import androidx.compose.runtime.Immutable
import com.yas.domain.requestsDb.states.AuthState
import com.yas.domain.requestsDb.states.BodyState

@Immutable
data class RequestModel(
    val id: Long,
    val label: String,
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
                label = "",
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


