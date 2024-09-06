package org.queryquill.app.data.requests.local

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.queryquill.app.core.model.GetTransactionModel
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.NewTransactionModel
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.core.model.Transaction
import org.queryquill.app.data.requests.local.dataSource.CurrentTransactionIdLocalDataSource
import org.queryquill.app.data.requests.local.dataSource.RequestLocalDataSource
import org.queryquill.app.data.requests.local.dataSource.ResponseLocalDataSource
import org.queryquill.app.data.requests.local.dataSource.TransactionLocalDataSource
import org.queryquill.app.data.requests.mappers.toDBO
import org.queryquill.app.data.requests.mappers.toDTO
import org.queryquill.app.data.requests.mappers.toModel
import org.queryquill.app.data.requests.models.ResponseDBO
import java.io.File
import java.net.URI

class TransactionsRepository internal constructor(
    private val context: Context,
    private val requestLocalDataSource: RequestLocalDataSource,
    private val currentTransactionIdLocalDataSource: CurrentTransactionIdLocalDataSource,
    private val transactionLocalDataSource: TransactionLocalDataSource,
    private val responseLocalDataSource: ResponseLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTransactions(): Flow<GetTransactionModel> {
        return currentTransactionIdLocalDataSource.getId().flowOn(ioDispatcher)
            .flatMapLatest { id ->
                transactionLocalDataSource.getTransactions().map { list ->
                    GetTransactionModel(ImmutableList(list.map { it.toModel() }), id)
                }
            }
    }

    fun getCurrentRequestOrNull(): Flow<RequestModel?> {
        return currentTransactionIdLocalDataSource.getId().flowOn(ioDispatcher)
            .map { value: Long? ->
                if (value != null) {
                    requestLocalDataSource.read(id = value)?.toModel()
                } else {
                    null
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentResponseOrNull(): Flow<ResponseModel?> {
        return currentTransactionIdLocalDataSource.getId().flowOn(ioDispatcher)
            .flatMapLatest { value: Long? ->
                if (value != null) {
                    responseLocalDataSource.read(id = value).map { responseDBO: ResponseDBO? ->
                        responseDBO?.toModel()
                    }
                } else {
                    flowOf(null)
                }
            }
    }

    suspend fun changeCurrentTransaction(id: Long?) {
        withContext(ioDispatcher) {
            currentTransactionIdLocalDataSource.saveId(id)
        }
    }


    suspend fun addTransaction(model: NewTransactionModel) {
        withContext(ioDispatcher) {
            transactionLocalDataSource.create(model.toDTO()).let { id ->
                requestLocalDataSource.create(id)
                responseLocalDataSource.create(id)
                currentTransactionIdLocalDataSource.saveId(id)
            }
        }
    }

    suspend fun updateRequest(model: RequestModel) {
        withContext(ioDispatcher) {
            requestLocalDataSource.update(model.toDBO())
        }
    }


    suspend fun deleteTransaction(id: Long) {
        withContext(ioDispatcher) {
            transactionLocalDataSource.delete(id).let {
                requestLocalDataSource.delete(id)
                responseLocalDataSource.delete(id)
            }
        }
    }

    suspend fun updateTransaction(model: Transaction) {
        withContext(ioDispatcher) {
            transactionLocalDataSource.update(model.toDBO())
        }
    }

    fun getFileUriByName(fileName: String): URI {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            file.writeText("")
        }
        return file.toURI()
    }
}