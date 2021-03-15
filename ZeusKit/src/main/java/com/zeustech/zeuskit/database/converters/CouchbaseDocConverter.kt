package com.zeustech.zeuskit.database.converters

import com.couchbase.lite.MutableDocument
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.zeustech.zeuskit.database.protocols.Id
import com.zeustech.zeuskit.database.TYPE
import com.zeustech.zeuskit.database.protocols.CouchbaseDocConverterProtocol
import com.zeustech.zeuskit.database.protocols.CouchbaseDocIdFinderProtocol
import java.util.HashMap

class CouchbaseDocConverter: ResultSetConverter(),
    CouchbaseDocConverterProtocol,
    CouchbaseDocIdFinderProtocol {

    override fun <T> couchbaseDocToMutableDoc(
        couchbaseDoc: T,
        documentType: String,
        clazz: Class<T>
    ): MutableDocument? {
        val map = couchbaseDocToMap(couchbaseDoc, documentType)
        val id = findCouchbaseDocId(couchbaseDoc, clazz)
        return if (map != null && id != null) MutableDocument(id, map) else null
    }

    override fun <T> couchbaseDocToMap(couchbaseDoc: T, documentType: String): Map<String, Any>? {
        try {
            val json = decoder.toJson(couchbaseDoc)
            val map = decoder.fromJson<MutableMap<String, Any>>(json, object : TypeToken<HashMap<String?, Any?>?>() {}.type)
            map[TYPE] = documentType
            return map
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
        return null
    }

    override fun <T> findCouchbaseDocId(couchbaseDoc: T, clazz: Class<T>): String? {
        try {
            for (field in clazz.declaredFields) {
                field.isAccessible = true
                if (!field.isAnnotationPresent(Id::class.java)) continue
                return field[couchbaseDoc] as String
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun <T> findCouchbaseDocsIds(couchbaseDocs: List<T>, clazz: Class<T>): List<String> {
        val ids: MutableList<String> = ArrayList()
        for (couchbaseDoc in couchbaseDocs) {
            val id = findCouchbaseDocId(couchbaseDoc, clazz) ?: continue
            ids.add(id)
        }
        return ids
    }

}