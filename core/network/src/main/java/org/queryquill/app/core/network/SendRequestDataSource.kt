package org.queryquill.app.core.network

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.prepareRequest
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readRemaining
import kotlinx.io.readByteArray
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.core.model.RequestModel
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.core.network.prepareRequest.applyAuth
import org.queryquill.app.core.network.prepareRequest.applyBody
import org.queryquill.app.core.network.prepareRequest.applyHeaders
import org.queryquill.app.core.network.prepareRequest.applyMethod
import org.queryquill.app.core.network.prepareRequest.applyUrlParameters
import org.queryquill.app.core.network.utils.CookieChecker
import org.queryquill.app.core.network.utils.calculateElapsedTime
import org.queryquill.app.core.network.utils.createErrorResponse
import org.queryquill.app.core.network.utils.extractHeaders
import org.queryquill.app.core.network.utils.formatUrl
import org.queryquill.app.core.network.utils.mimeTypeToContentType
import java.io.File

class SendRequestDataSource(
    private val client: HttpClient,
    private val context: Context
) {

    suspend fun sendRequest(model: RequestModel, cookies: List<String>): ResponseModel {
        val fileNamePrefix = "${model.id}_response"
        return try {
            val file = File(context.filesDir, "$fileNamePrefix.txt").apply { writeText("") }
            val request = client.prepareRequest(formatUrl(model.url)) {

                applyUrlParameters(model.query.list)
                applyMethod(model.type)
                applyHeaders(model.header.list)
                applyBody(model.bodyState, context)
                applyAuth(model.auth)
                val relCookie = CookieChecker.getRelevantCookies(formatUrl(model.url), cookies)
                if (relCookie.isNotEmpty()) {
                    headers.append("Cookie", relCookie.joinToString("; "))
                }
            }
            val response = executeRequest(request, file)
            processResponse(response, file, fileNamePrefix, model.id)

        } catch (e: Exception) {
            handleRequestError(e, fileNamePrefix, model.id)
        }
    }

    private suspend fun executeRequest(request: HttpStatement, file: File): HttpResponse {
        return request.execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()
            while (!channel.isClosedForRead) {
                val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                while (!packet.exhausted()) {
                    file.appendBytes(packet.readByteArray())
                }
            }
            httpResponse
        }
    }

    private fun processResponse(
        response: HttpResponse, file: File, fileNamePrefix: String, requestId: Long
    ): ResponseModel {
        val elapsedTime = calculateElapsedTime(response)
        val contentLength = file.length().toString()
        val status = response.status.value.toString()
        val contentType =
            mimeTypeToContentType("${response.contentType()?.contentType}/${response.contentType()?.contentSubtype}")
        val fileName = "${fileNamePrefix}.${contentType.fileType}"
        val newFile = File(file.parentFile, fileName)
        file.renameTo(newFile)
        val headers = extractHeaders(response.headers)
        return ResponseModel(
            id = requestId,
            status = status,
            fileName = fileName,
            contentLength = contentLength,
            time = elapsedTime,
            contentType = contentType,
            headers = ImmutableList(headers)
        )
    }

    private fun handleRequestError(
        e: Exception, fileNamePrefix: String, requestId: Long
    ): ResponseModel {
        val errorMessage = when (e) {
            is UnresolvedAddressException -> "Couldn't resolve host name"
            is NullPointerException -> "File not found"
            else -> e.message.toString()
        }
        val fileName = "$fileNamePrefix.txt"
        File(context.filesDir, fileName).writeText(errorMessage)
        return createErrorResponse(fileName, requestId)
    }
}
