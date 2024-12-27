package org.queryquill.app.core.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, val label: String
)
