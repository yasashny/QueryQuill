package ru.yasdev.data.requestsDb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yasdev.domain.requestsDb.models.Body
import ru.yasdev.domain.requestsDb.models.HttpType
import ru.yasdev.domain.requestsDb.models.ListItem

@Entity
data class DataRequestModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val label: String,
    val body: Body,
    val header: List<ListItem>,
    val query: List<ListItem>,
    val type: HttpType,
    val url: String
)
