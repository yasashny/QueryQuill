package org.queryquill.app.core.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.queryquill.app.core.database.RequestDataSource
import org.queryquill.app.core.database.ResponseDataSource
import org.queryquill.app.core.database.TransactionDataSource
import org.queryquill.app.core.datastore.CurrentTransactionIdDataSource
import org.queryquill.app.core.model.GetTransactionModel
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.NewTransactionModel
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.core.model.Transaction

class TransactionsRepository internal constructor(
    private val requestDataSource: RequestDataSource,
    private val currentTransactionIdDataSource: CurrentTransactionIdDataSource,
    private val transactionDataSource: TransactionDataSource,
    private val responseDataSource: ResponseDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTransactions(): Flow<GetTransactionModel> {
        return currentTransactionIdDataSource.getId().flowOn(ioDispatcher)
            .flatMapLatest { id ->
                transactionDataSource.getTransactions().map { list ->
                    GetTransactionModel(ImmutableList(list.map { it }), id)
                }
            }
    }

    fun getCurrentRequestOrNull(): Flow<RequestModel?> {
        return currentTransactionIdDataSource.getId().flowOn(ioDispatcher)
            .map { value: Long? ->
                if (value != null) {
                    requestDataSource.read(id = value)
                } else {
                    null
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentResponseOrNull(): Flow<ResponseModel?> {
        return currentTransactionIdDataSource.getId().flowOn(ioDispatcher)
            .flatMapLatest { value: Long? ->
                if (value != null) {
                    responseDataSource.read(id = value)
                } else {
                    flowOf(null)
                }
            }
    }

    suspend fun changeCurrentTransaction(id: Long?) {
        withContext(ioDispatcher) {
            currentTransactionIdDataSource.saveId(id)
        }
    }


    suspend fun addTransaction(model: NewTransactionModel) {
        withContext(ioDispatcher) {
            transactionDataSource.create(model).let { id ->
                requestDataSource.create(id)
                responseDataSource.create(id)
                currentTransactionIdDataSource.saveId(id)
            }
        }
    }

    suspend fun updateRequest(model: RequestModel) {
        withContext(ioDispatcher) {
            requestDataSource.update(model)
        }
    }


    suspend fun deleteTransaction(id: Long) {
        withContext(ioDispatcher) {
            transactionDataSource.delete(id).let {
                requestDataSource.delete(id)
                responseDataSource.delete(id)
            }
        }
    }

    suspend fun updateTransaction(model: Transaction) {
        withContext(ioDispatcher) {
            transactionDataSource.update(model)
        }
    }
}