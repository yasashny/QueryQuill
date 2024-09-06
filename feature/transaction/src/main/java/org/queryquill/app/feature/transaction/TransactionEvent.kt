package org.queryquill.app.feature.transaction

import org.queryquill.app.core.model.Transaction

internal sealed interface TransactionEvent {
    data class DeleteTransaction(val id: Long) : TransactionEvent
    data class SetTransaction(val id: Long?) : TransactionEvent
    data class UpdateTransaction(val transaction: Transaction) : TransactionEvent
}