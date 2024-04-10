package ru.yasdev.data.requestsDb.models

import android.net.Uri
import ru.yasdev.domain.requestsDb.models.ListItem
import ru.yasdev.domain.requestsDb.models.MultipartFormState

data class MultipartFormDataModel(
    val listItem: ListItem?,
    val uri: Uri?
)
