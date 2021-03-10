package com.nativeboys.couchbase.lite.android.dao.converters

import com.couchbase.lite.ResultSet
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.nativeboys.couchbase.lite.android.dao.protocols.ResultSetConverterProtocol
import java.util.ArrayList

open class ResultSetConverter:
    ResultSetConverterProtocol {

    protected val decoder: Gson = Gson()

    override fun <T> mapToObject(map: Map<String, Any>, clazz: Class<T>): T? {
        try {
            return decoder.fromJson(decoder.toJson(map), clazz)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
        return null
    }

    override fun <T> resultSetToObject(resultSet: ResultSet, clazz: Class<T>): List<T> {
        val list: MutableList<T> = ArrayList()
        for (result in resultSet.allResults()) {
            val map: Map<String, Any> = (
                    if (result.count() > 0) result.getDictionary(0)?.toMap() ?: result.toMap()
                    else null
                    ) ?: continue
            val obj = mapToObject(map, clazz) ?: continue
            list.add(obj)
        }
        return list
    }

}