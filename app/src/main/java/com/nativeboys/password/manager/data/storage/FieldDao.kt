package com.nativeboys.password.manager.data.storage

import androidx.room.*
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.data.FieldData

@Dao
interface FieldDao {

    @Query("SELECT id FROM fields WHERE category_id == :categoryId")
    suspend fun findAllIdsByCategoryId(categoryId: String): List<String>

    @Query("SELECT * FROM fields WHERE id == :id")
    suspend fun findById(id: String): FieldData

    @Query("SELECT * FROM fields WHERE fields.id IN (:ids)")
    suspend fun findByIds(ids: List<String>): List<FieldData>

    @Query("SELECT * FROM fields WHERE fields.category_id == :categoryId")
    suspend fun findAllByCategoryId(categoryId: String): List<FieldData>

    @Insert
    suspend fun save(fields: List<FieldData>)

    @Update
    suspend fun update(fields: List<FieldData>)

    @Delete
    suspend fun delete(fields: List<FieldData>)

    @Query("DELETE FROM fields WHERE category_id =:categoryId AND id NOT IN (:fieldIds)")
    suspend fun deleteAllExcept(categoryId: String, fieldIds: List<String>)

    @Query("""
        SELECT mContents.id AS contentId, mContents.content AS textContent, mFields.id AS fieldId, mFields.name AS fieldName, mFields.type AS fieldType
        FROM (SELECT * FROM fields WHERE category_id = :categoryId) AS mFields
        LEFT JOIN (SELECT * FROM contents WHERE contents.item_id = :itemId) AS mContents
        ON mFields.id = mContents.field_id
     """)
    suspend fun findAllDtoByCategoryIdAndItemId(categoryId: String, itemId: String): List<FieldContentDto>

}