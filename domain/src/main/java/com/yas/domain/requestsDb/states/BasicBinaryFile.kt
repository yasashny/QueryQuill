package com.yas.domain.requestsDb.states

import android.net.Uri
import kotlinx.serialization.Serializable


sealed class BasicBinaryFile {
    abstract val uri: Uri
    abstract val fileName: String
}