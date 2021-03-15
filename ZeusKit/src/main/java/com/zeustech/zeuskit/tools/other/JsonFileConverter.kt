package com.zeustech.zeuskit.tools.other

import android.content.Context
import androidx.annotation.RawRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import java.io.IOException
import java.nio.charset.StandardCharsets

class JsonFileConverter {

    private suspend fun read(
        context: Context,
        @RawRes file: Int
    ): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            context.resources.openRawResource(file).use { inputStream ->
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                String(buffer, StandardCharsets.UTF_8)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    suspend fun <T> convert(
        context: Context,
        @RawRes file: Int,
        strategy: DeserializationStrategy<T>
    ): T? = withContext(Dispatchers.IO) {
        return@withContext try {
            read(context, file)?.let { Json.decodeFromString(strategy, it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}