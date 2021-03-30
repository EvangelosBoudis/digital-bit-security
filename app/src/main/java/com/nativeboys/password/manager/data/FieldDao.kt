package com.nativeboys.password.manager.data

import androidx.room.*

@Dao
interface FieldDao {

    @Query("SELECT * FROM fields WHERE id == :id")
    suspend fun findById(id: String): FieldEntity

    @Query("SELECT * FROM fields WHERE fields.id IN (:ids)")
    suspend fun findByIds(ids: List<String>): List<FieldEntity>

    @Query("SELECT * FROM fields WHERE fields.category_id == :categoryId")
    suspend fun findByCategoryId(categoryId: String): List<FieldEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(fields: List<FieldEntity>)

    @Update
    suspend fun update(fields: List<FieldEntity>)

    @Delete
    suspend fun delete(fields: List<FieldEntity>)

}