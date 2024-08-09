package com.yas.model

data class GetTransactionModel(val list: ImmutableList<Transaction>, val currentId: Long?)
