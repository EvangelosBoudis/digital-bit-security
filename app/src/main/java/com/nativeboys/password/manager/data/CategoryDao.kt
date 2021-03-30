package com.nativeboys.password.manager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    // (Multiple) -> ORDER BY name ASC, date_modified DESC

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchKey || '%' ORDER BY name ASC")
    fun findByNameSortedByName(searchKey: String): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchKey || '%' ORDER BY date_modified DESC")
    fun findByNameSortedByDateModified(searchKey: String): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categories: List<CategoryEntity>)

    @Update
    suspend fun update(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

}