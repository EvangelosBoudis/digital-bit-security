package com.nativeboys.couchbase.lite.android.dao

import com.couchbase.lite.*
import com.nativeboys.couchbase.lite.android.dao.extensions.toObjects
import com.nativeboys.couchbase.lite.android.dao.converters.CouchbaseDocConverter
import com.nativeboys.couchbase.lite.android.dao.extensions.asObjectsFlow
import com.nativeboys.couchbase.lite.android.dao.protocols.CouchbaseDocument
import com.nativeboys.couchbase.lite.android.dao.protocols.Dao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

open class CouchbaseDao<T>(val database: Database, private val clazz: Class<T>):
    Dao<T> {

    private val converter = CouchbaseDocConverter()

    private val documentType: String by lazy {
        var type = clazz.simpleName
        if (clazz.isAnnotationPresent(CouchbaseDocument::class.java)) {
            val ann = clazz.getAnnotation(CouchbaseDocument::class.java)
            ann?.type?.let {
                if (it.isNotEmpty()) type = it
            }
        }
        type
    }

    @ExperimentalCoroutinesApi
    fun observeAll(): Flow<List<T>> {

        val query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.database(database))
            .where(Expression.property(TYPE).equalTo(Expression.string(documentType)))

        return observe(query)
    }

    @ExperimentalCoroutinesApi
    fun observe(query: Query): Flow<List<T>> = query.asObjectsFlow(converter = converter, clazz = clazz)

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Read
    //////////////////////////////////////////////////////////////////////////////////////////

    override suspend fun findOne(): T? = withContext(Dispatchers.IO) {

        val query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.database(database))
            .where(Expression.property(TYPE).equalTo(Expression.string(documentType)))
            .limit(Expression.intValue(1))

        return@withContext executeAndConvert(query).firstOrNull()
    }

    override suspend fun findAll(): List<T> = withContext(Dispatchers.IO) {

        val query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.database(database))
            .where(Expression.property(TYPE).equalTo(Expression.string(documentType)))

        return@withContext executeAndConvert(query)
    }

    override suspend fun findAll(limit: Int, skip: Int, asc: Boolean, vararg orderBy: String): List<T>
            = withContext(Dispatchers.IO) {

        val ordering = orderBy.map {
            val expression = Ordering.expression(Expression.property(it))
            if (asc) {
                expression.ascending()
            } else {
                expression.descending()
            }
        }.toTypedArray()

        val query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.database(database))
            .where(Expression.property(TYPE).equalTo(Expression.string(documentType)))
            .orderBy(*ordering)
            .limit(Expression.intValue(limit), Expression.intValue(skip))

        return@withContext executeAndConvert(query)
    }

    override suspend fun findById(id: String): T? = withContext(Dispatchers.IO) {
        return@withContext findByIds(arrayListOf(id)).firstOrNull()
    }

    override suspend fun findByIds(ids: List<String>): List<T> = withContext(Dispatchers.IO) {

        val values = ids.map {
            Expression.string(it)
        }.toTypedArray()

        val query: Query = QueryBuilder
            .select(SelectResult.all())
            .from(DataSource.database(database))
            .where(Meta.id.`in`(*values))

        return@withContext executeAndConvert(query)
    }

    override suspend fun findAllIds(): List<String> = withContext(Dispatchers.IO) {

        val query = QueryBuilder
            .select(SelectResult.expression(Meta.id))
            .from(DataSource.database(database))
            .where(Expression.property(TYPE).equalTo(Expression.string(documentType)))

        val results: MutableList<Result> = ArrayList()
        try {
            results.addAll(query.execute().allResults())
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
        val ids: MutableList<String> = ArrayList()
        for (result in results) {
            val id = result.getString(ID) ?: continue
            ids.add(id)
        }
        return@withContext ids
    }

    private fun findDocumentsByIds(ids: List<String>): List<Document> {
        val documents: MutableList<Document> = ArrayList()
        for (id in ids) {
            val document = database.getDocument(id) ?: continue
            documents.add(document)
        }
        return documents
    }

    private suspend fun findAllDocuments(): List<Document> {
        return findDocumentsByIds(findAllIds())
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Create
    //////////////////////////////////////////////////////////////////////////////////////////

    override suspend fun save(t: T): Boolean = withContext(Dispatchers.IO) {
        return@withContext save(arrayListOf(t), bulk = false)
    }

    // Estimated time for 3000 records = 2 seconds
    override suspend fun save(ts: List<T>, bulk: Boolean): Boolean = withContext(Dispatchers.IO) {
        val mutableDocs: MutableList<MutableDocument> = ArrayList()
        for (couchbaseDoc in ts) {
            val mutableDoc = converter.couchbaseDocToMutableDoc(couchbaseDoc, documentType, clazz) ?: continue
            mutableDocs.add(mutableDoc)
        }
        var success = false
        if (bulk) {
            try {
                database.inBatch {
                    success = saveMutableDocs(mutableDocs)
                }
            } catch (e: CouchbaseLiteException) {
                e.printStackTrace()
            }
        } else {
            success = saveMutableDocs(mutableDocs)
        }
        return@withContext success
    }

    private fun saveMutableDocs(mutableDocs: List<MutableDocument>): Boolean {
        for (mutableDoc in mutableDocs) {
            try {
                database.save(mutableDoc)
            } catch (e: CouchbaseLiteException) {
                e.printStackTrace()
                return false
            }
        }
        return true
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Delete
    //////////////////////////////////////////////////////////////////////////////////////////

    override suspend fun deleteById(id: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext deleteByIds(arrayListOf(id), bulk = false)
    }

    override suspend fun delete(t: T): Boolean = withContext(Dispatchers.IO) {
        return@withContext delete(arrayListOf(t), bulk = false)
    }

    override suspend fun deleteByIds(ids: List<String>, bulk: Boolean): Boolean = withContext(Dispatchers.IO) {
        val documents = findDocumentsByIds(ids)
        var success = false
        if (bulk) {
            try {
                database.inBatch {
                    success = deleteDocuments(documents)
                }
            } catch (e: CouchbaseLiteException) {
                e.printStackTrace()
            }
        } else {
            success = deleteDocuments(documents)
        }
        return@withContext success
    }

    override suspend fun delete(ts: List<T>, bulk: Boolean): Boolean = withContext(Dispatchers.IO) {
        return@withContext deleteByIds(converter.findCouchbaseDocsIds(ts, clazz), bulk)
    }

    override suspend fun deleteAll(bulk: Boolean): Boolean = withContext(Dispatchers.IO) {
        return@withContext deleteByIds(findAllIds(), bulk)
    }

    private fun deleteDocuments(documents: List<Document>): Boolean {
        for (document in documents) {
            try {
                database.delete(document)
            } catch (e: CouchbaseLiteException) {
                e.printStackTrace()
                return false
            }
        }
        return true
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Update
    //////////////////////////////////////////////////////////////////////////////////////////

    override suspend fun update(t: T): Boolean = withContext(Dispatchers.IO) {
        return@withContext update(arrayListOf(t), bulk = false)
    }

    override suspend fun update(ts: List<T>, bulk: Boolean): Boolean = withContext(Dispatchers.IO) {
        var success = false
        val ids = converter.findCouchbaseDocsIds(ts, clazz)
        val documents = findDocumentsByIds(ids)
        val mutableDocs: MutableList<MutableDocument> = ArrayList()
        if (ts.size == documents.size) {
            for (i in documents.indices) {
                val map = converter.couchbaseDocToMap(ts[i], documentType) ?: continue
                mutableDocs.add(documents[i].toMutable().setData(map))
            }
            if (bulk) {
                try {
                    database.inBatch {
                        success = saveMutableDocs(mutableDocs)
                    }
                } catch (e: CouchbaseLiteException) {
                    e.printStackTrace()
                }
            } else {
                success = saveMutableDocs(mutableDocs)
            }
        }
        return@withContext success
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Replace
    //////////////////////////////////////////////////////////////////////////////////////////

    override suspend fun replace(t: T): Boolean = withContext(Dispatchers.IO) {
        return@withContext replace(arrayListOf(t), bulk = false)
    }

    override suspend fun replace(ts: List<T>, bulk: Boolean): Boolean = withContext(Dispatchers.IO) {
        val documents = findAllDocuments()
        val mutableDocs: MutableList<MutableDocument> = ArrayList()
        for (couchbaseDoc in ts) {
            val mutableDoc = converter.couchbaseDocToMutableDoc(couchbaseDoc, documentType, clazz) ?: continue
            mutableDocs.add(mutableDoc)
        }
        var success = false
        if (bulk) {
            try {
                database.inBatch {
                    success = deleteDocuments(documents) && saveMutableDocs(mutableDocs)
                }
            } catch (e: CouchbaseLiteException) {
                e.printStackTrace()
            }
        } else {
            success = deleteDocuments(documents) && saveMutableDocs(mutableDocs)
        }
        return@withContext success
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Tools
    //////////////////////////////////////////////////////////////////////////////////////////

    suspend fun executeAndConvert(query: Query): List<T> = withContext(Dispatchers.IO) {
        val couchbaseDocs: MutableList<T> = ArrayList()
        try {
            couchbaseDocs.addAll(query.execute().toObjects(converter, clazz))
        } catch (e: CouchbaseLiteException) {
            e.printStackTrace()
        }
        return@withContext couchbaseDocs
    }

}