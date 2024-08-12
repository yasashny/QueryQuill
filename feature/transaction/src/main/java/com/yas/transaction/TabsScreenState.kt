package com.yas.transaction

enum class TabsScreenState(val pageIndex: Int) {
    REQUEST(0), RESPONSE(1)
}

fun selectPage(page: Int): TabsScreenState {
    return when (page) {
        0 -> TabsScreenState.REQUEST
        else -> TabsScreenState.RESPONSE
    }
}

