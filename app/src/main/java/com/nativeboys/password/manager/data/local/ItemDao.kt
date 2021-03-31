package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.ItemData
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items WHERE id == :id")
    suspend fun findById(id: String): ItemData

    @Query("SELECT * FROM items WHERE category_id == :categoryId")
    fun findByCategoryId(categoryId: String): Flow<List<ItemData>>

    @Query("SELECT * FROM items WHERE favorite == :favorite")
    fun findByFavorite(favorite: Boolean = true): Flow<List<ItemData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: ItemData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(items: List<ItemData>)

    @Update
    suspend fun update(item: ItemData)

    @Delete
    suspend fun delete(item: ItemData)

}