package com.nativeboys.couchbase.lite.android.dao.extensions

import android.os.AsyncTask
import com.couchbase.lite.*
import com.nativeboys.couchbase.lite.android.dao.protocols.ResultSetConverterProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.concurrent.Executor

@ExperimentalCoroutinesApi
fun Query.asResultFlow(
    executor: Executor = AsyncTask.SERIAL_EXECUTOR,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Flow<ResultSet> = asQueryChangeFlow(executor).map { it.results }.flowOn(dispatcher)

@ExperimentalCoroutinesApi
fun <T> Query.asObjectsFlow(
    executor: Executor = AsyncTask.SERIAL_EXECUTOR,
    converter: ResultSetConverterProtocol,
    clazz: Class<T>
): Flow<List<T>> {
    return asQueryChangeFlow(executor).map {
        converter.resultSetToObject(it.results, clazz)
    }.flowOn(Dispatchers.IO)
}

@ExperimentalCoroutinesApi
private fun Query.asQueryChangeFlow(executor: Executor): Flow<QueryChange> = callbackFlow {
    val token = addChangeListener(executor) {
        try {
            if (it.error == null) {
                offer(it)
            } else {
                throw it.error
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
/*  try {
        awaitClose { removeChangeListener(token) }
    } catch (e: Exception) {
        e.printStackTrace()
    }*/
    awaitClose { removeChangeListener(token) }
}.flowOn(Dispatchers.IO)

suspend fun <T> Query.toObject(
    converter: ResultSetConverterProtocol,
    clazz: Class<T>
): List<T> = withContext(Dispatchers.IO) {
    return@withContext try {
        execute().toObjects(converter, clazz)
    } catch (e: CouchbaseLiteException) {
        ArrayList()
    }
}

/** For One Shot Operations with Callback based Api use suspendCancellableCoroutine
 *  https://www.youtube.com/watch?v=B8ppnjGPAGE&t=932s&ab_channel=AndroidDevelopers
 * */