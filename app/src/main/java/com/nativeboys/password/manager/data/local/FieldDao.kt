package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.FieldData

@Dao
interface FieldDao {

    @Query("SELECT * FROM fields WHERE id == :id")
    suspend fun findById(id: String): FieldData

    @Query("SELECT * FROM fields WHERE fields.id IN (:ids)")
    suspend fun findByIds(ids: List<String>): List<FieldData>

    @Query("SELECT * FROM fields WHERE fields.category_id == :categoryId")
    suspend fun findAllByCategoryId(categoryId: String): List<FieldData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(fields: List<FieldData>)

    @Update
    suspend fun update(fields: List<FieldData>)

    @Delete
    suspend fun delete(fields: List<FieldData>)

}