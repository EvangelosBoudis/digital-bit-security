package com.nativeboys.password.manager.data.dao

import androidx.room.*
import com.nativeboys.password.manager.data.CategoryData
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // (Multiple) -> ORDER BY name ASC, date_modified DESC

    @Query("SELECT * FROM categories WHERE id == :id")
    suspend fun findById(id: String): CategoryData

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchKey || '%' ORDER BY name ASC")
    fun findByNameSortedByName(searchKey: String): Flow<List<CategoryData>>

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchKey || '%' ORDER BY date_modified DESC")
    fun findByNameSortedByDateModified(searchKey: String): Flow<List<CategoryData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(category: CategoryData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categories: List<CategoryData>)

    @Update
    suspend fun update(category: CategoryData)

    @Delete
    suspend fun delete(category: CategoryData)

}