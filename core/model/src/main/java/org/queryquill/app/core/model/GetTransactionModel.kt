package org.queryquill.app.core.model

data class GetTransactionModel(val list: ImmutableList<Transaction>, val currentId: Long?)
