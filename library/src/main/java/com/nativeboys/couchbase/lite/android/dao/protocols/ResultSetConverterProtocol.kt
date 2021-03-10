package com.nativeboys.couchbase.lite.android.dao.protocols

import com.couchbase.lite.ResultSet

interface ResultSetConverterProtocol {
    fun <T> mapToObject(map: Map<String, Any>, clazz: Class<T>): T?
    fun <T> resultSetToObject(resultSet: ResultSet, clazz: Class<T>): List<T>
}