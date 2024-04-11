package ru.yasdev.domain.requestsDb.states

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
sealed class BasicBinaryFile {
    abstract val uri: Uri
}