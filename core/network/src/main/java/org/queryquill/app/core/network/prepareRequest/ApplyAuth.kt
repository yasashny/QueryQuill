package org.queryquill.app.core.network.prepareRequest

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpHeaders
import org.queryquill.app.core.model.AuthState
import org.queryquill.app.core.network.utils.encodeBase64

internal fun HttpRequestBuilder.applyAuth(authState: AuthState) {
    when (authState) {
        is AuthState.Basic -> {
            handleBasicAuthState(authState)
        }

        AuthState.NoAuth -> {}
    }
}

private fun HttpRequestBuilder.handleBasicAuthState(authState: AuthState.Basic) {
    headers.append(
        HttpHeaders.Authorization,
        "Basic " + encodeBase64("${authState.userName}:${authState.password}")
    )
}