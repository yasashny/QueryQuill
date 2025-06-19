package org.queryquill.app.core.model

import androidx.annotation.StringRes

interface HasLabelRes {
    @get:StringRes
    val labelRes: Int
}