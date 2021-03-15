package com.zeustech.zeuskit.database.protocols

interface Dao<T> {

    suspend fun findOne(): T?

    suspend fun findAll(): List<T>

    suspend fun findAll(limit: Int, skip: Int, asc: Boolean, vararg orderBy: String): List<T>

    suspend fun findById(id: String): T?

    suspend fun findByIds(ids: List<String>): List<T>

    suspend fun findAllIds(): List<String>

    suspend fun save(t: T): Boolean

    suspend fun save(ts: List<T>, bulk: Boolean = true): Boolean

    suspend fun deleteById(id: String): Boolean

    suspend fun delete(t: T): Boolean

    suspend fun deleteByIds(ids: List<String>, bulk: Boolean = true): Boolean

    suspend fun delete(ts: List<T>, bulk: Boolean = true): Boolean

    suspend fun deleteAll(bulk: Boolean = true): Boolean

    suspend fun update(t: T): Boolean

    suspend fun update(ts: List<T>, bulk: Boolean = true): Boolean

    suspend fun replace(t: T): Boolean

    suspend fun replace(ts: List<T>, bulk: Boolean = true): Boolean

}