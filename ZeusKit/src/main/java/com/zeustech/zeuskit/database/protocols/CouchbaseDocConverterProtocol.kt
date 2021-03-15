package com.zeustech.zeuskit.database.protocols

import com.couchbase.lite.MutableDocument

interface CouchbaseDocConverterProtocol:
    ResultSetConverterProtocol {
    fun <T> couchbaseDocToMutableDoc(couchbaseDoc: T, documentType: String, clazz: Class<T>): MutableDocument?
    fun <T> couchbaseDocToMap(couchbaseDoc: T, documentType: String): Map<String, Any>?
}