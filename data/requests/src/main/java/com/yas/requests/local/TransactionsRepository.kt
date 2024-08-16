package com.yas.requests.local

import android.content.Context
import com.yas.model.GetTransactionModel
import com.yas.model.ImmutableList
import com.yas.model.NewTransactionModel
import com.yas.model.RequestModel
import com.yas.model.ResponseModel
import com.yas.model.Transaction
import com.yas.requests.local.dataSource.CurrentTransactionIdLocalDataSource
import com.yas.requests.local.dataSource.RequestLocalDataSource
import com.yas.requests.local.dataSource.ResponseLocalDataSource
import com.yas.requests.local.dataSource.TransactionLocalDataSource
import com.yas.requests.mappers.toDBO
import com.yas.requests.mappers.toDTO
import com.yas.requests.mappers.toModel
import com.yas.requests.models.RequestDBO
import com.yas.requests.models.ResponseDBO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
        return transactionLocalDataSource.getTransactions().flowOn(ioDispatcher).map { list ->
            list.map { it.toModel() }
        }.flatMapLatest { list ->
            currentTransactionIdLocalDataSource.getId().map {
                GetTransactionModel(ImmutableList(list), it)
            }

        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentRequestOrNull(): Flow<RequestModel?> {
        return currentTransactionIdLocalDataSource.getId().flowOn(ioDispatcher)
            .flatMapLatest { value: Long? ->
                if (value != null) {
                    requestLocalDataSource.read(id = value).map { requestDBO: RequestDBO? ->
                        requestDBO?.toModel()
                    }
                } else {
                    flowOf(null)
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