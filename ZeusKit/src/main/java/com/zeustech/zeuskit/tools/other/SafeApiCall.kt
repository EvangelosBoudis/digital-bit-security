package com.zeustech.zeuskit.tools.other

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import retrofit2.Response

data class ResultWrapper<S, F>(
    val successModel: S? = null,
    val failureModel: F? = null,
    val statusCode: Int = -1
)

object SafeApiCall {

    private val TAG = javaClass.simpleName

    suspend fun <S, F> call(
        successStrategy : DeserializationStrategy<S>,
        failureStrategy: DeserializationStrategy<F>,
        apiCall: suspend () -> Response<ResponseBody>
    ): ResultWrapper<S, F> {
        return withContext(Dispatchers.IO) {
            try {
                apiCall.invoke()
            } catch (e: Exception) {
                null
            }?.let { response ->
                val body = response.body()
                val errorBody = response.errorBody()
                var successModel: S? = null
                var failureModel: F? = null
                val json = Json {
                    allowStructuredMapKeys = true
                    ignoreUnknownKeys = true
                }
                Log.i(TAG, "Status: ${response.code()}")
                body?.let {
                    try {
                        val text = it.string()
                        Log.i(TAG, "Response: $text")
                        try {
                            successModel = json.decodeFromString(successStrategy, text)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        if (successModel == null) failureModel = json.decodeFromString(failureStrategy, text)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                if (successModel == null && failureModel == null) {
                    errorBody?.let {
                        val text = it.string()
                        Log.i(TAG, "Error: $text")
                        try {
                            failureModel = json.decodeFromString(failureStrategy, text)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                body?.close()
                errorBody?.close()
                return@withContext ResultWrapper(successModel, failureModel, response.code())
            }
            return@withContext ResultWrapper()
        }
    }

}