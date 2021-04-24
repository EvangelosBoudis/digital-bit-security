package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.CategoryWithFields
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // (Multiple) -> ORDER BY name ASC, date_modified DESC

    @Query("SELECT * FROM categories WHERE id == :id")
    suspend fun findById(id: String): CategoryData

    @Query("SELECT * FROM categories ORDER BY name")
    suspend fun findAll(): List<CategoryData>

    @Query("SELECT * FROM categories ORDER BY name")
    fun observeAll(): Flow<List<CategoryData>>

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchKey || '%' ORDER BY name ASC")
    fun observeAllByNameSortedByName(searchKey: String): Flow<List<CategoryData>>

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchKey || '%' ORDER BY date_modified DESC")
    fun observeAllByNameSortedByDateModified(searchKey: String): Flow<List<CategoryData>>

    @Transaction
    @Query("SELECT * FROM categories WHERE id == :id")
    suspend fun findCategoryWithFieldsById(id: String): CategoryWithFields

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(category: CategoryData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categories: List<CategoryData>)

    @Update
    suspend fun update(category: CategoryData)

    @Delete
    suspend fun delete(category: CategoryData)

    //    @Query(
//        """
//            SELECT id AS entityId, COUNT(id) rowsCount
//            FROM thumbnails
//            WHERE thumbnails.id IN (:thumbnailIds)
//            GROUP BY entityId
//            """)
//    suspend fun countByThumbnailId(thumbnailIds: List<String>): List<CountDto>

}