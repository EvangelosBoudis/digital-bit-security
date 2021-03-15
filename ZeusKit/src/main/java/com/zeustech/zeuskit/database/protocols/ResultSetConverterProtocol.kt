package com.zeustech.zeuskit.database.protocols

import com.couchbase.lite.ResultSet

interface ResultSetConverterProtocol {
    fun <T> mapToObject(map: Map<String, Any>, clazz: Class<T>): T?
    fun <T> resultSetToObject(resultSet: ResultSet, clazz: Class<T>): List<T>
}