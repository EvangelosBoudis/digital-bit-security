package com.nativeboys.couchbase.lite.android.dao.extensions

import com.couchbase.lite.ResultSet
import com.nativeboys.couchbase.lite.android.dao.protocols.ResultSetConverterProtocol

fun <T> ResultSet.toObjects(converter: ResultSetConverterProtocol, clazz: Class<T>): List<T> = converter.resultSetToObject(this, clazz)