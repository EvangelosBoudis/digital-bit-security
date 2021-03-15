package com.zeustech.zeuskit.database.protocols

interface CouchbaseDocIdFinderProtocol {
    fun <T> findCouchbaseDocId(couchbaseDoc: T, clazz: Class<T>): String?
    fun <T> findCouchbaseDocsIds(couchbaseDocs: List<T>, clazz: Class<T>): List<String>
}