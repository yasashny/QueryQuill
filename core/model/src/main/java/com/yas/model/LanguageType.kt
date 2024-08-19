package com.yas.model

import android.os.Build
import androidx.annotation.RequiresApi

// For code editor

enum class LanguageType(val code: String?) {
    @RequiresApi(Build.VERSION_CODES.O)
    HTML("text.html.basic"),

    @RequiresApi(Build.VERSION_CODES.O)
    JSON("source.json"),

    PLAIN(null),

    OTHER(null),

    @RequiresApi(Build.VERSION_CODES.O)
    XML("text.xml")
}