package com.nativeboys.couchbase.lite.android.dao.protocols

interface CouchbaseDocIdFinderProtocol {
    fun <T> findCouchbaseDocId(couchbaseDoc: T, clazz: Class<T>): String?
    fun <T> findCouchbaseDocsIds(couchbaseDocs: List<T>, clazz: Class<T>): List<String>
}