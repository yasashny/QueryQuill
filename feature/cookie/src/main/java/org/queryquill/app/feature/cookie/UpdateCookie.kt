package org.queryquill.app.feature.cookie

internal sealed interface UpdateCookie {
    data object Add : UpdateCookie
    data class Delete(val id: Int) : UpdateCookie
    data class Update(val id: Int, val newCookieState: CookieModel) : UpdateCookie
}