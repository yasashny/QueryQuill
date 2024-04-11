package ru.yasdev.domain.requestsDb.models

import android.net.Uri
import kotlinx.serialization.Serializable

@Serializable
sealed class BasicBinaryFile{
    abstract val uri:  Uri
}