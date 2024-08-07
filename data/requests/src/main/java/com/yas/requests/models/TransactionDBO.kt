package com.yas.requests.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class TransactionDBO(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, val label: String
)
