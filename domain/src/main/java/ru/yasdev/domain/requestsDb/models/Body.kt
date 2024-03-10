package ru.yasdev.domain.requestsDb.models

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
sealed interface Body {

    data class Text(val text: String) : Body
    data object MultipartForm: Body
    data class FormUrlEncoded(val list: List<ListItem>): Body
    data class BinaryFile(val uri: Uri): Body
    data object NoBody: Body

}