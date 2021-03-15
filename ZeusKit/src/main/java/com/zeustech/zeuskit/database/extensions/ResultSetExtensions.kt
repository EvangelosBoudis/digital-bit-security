package com.zeustech.zeuskit.database.extensions

import com.couchbase.lite.ResultSet
import com.zeustech.zeuskit.database.protocols.ResultSetConverterProtocol

fun <T> ResultSet.toObjects(converter: ResultSetConverterProtocol, clazz: Class<T>): List<T> = converter.resultSetToObject(this, clazz)