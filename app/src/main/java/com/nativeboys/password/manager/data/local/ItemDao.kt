package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.ItemData
import com.nativeboys.password.manager.data.ItemDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    suspend fun findAll(): List<ItemData>

    @Query("""
        SELECT items.id AS itemId, items.name AS itemName, items.description AS itemDescription, items.category_id AS itemCategoryId, thumbnails.url AS thumbnailUrl 
        FROM items
        LEFT JOIN thumbnails ON thumbnails.id = items.thumbnail_id
        GROUP BY itemId
        """)
    fun findAllDtoAsFlow(): Flow<List<ItemDto>>

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

    @Query("DELETE FROM items WHERE items.id == :id")
    suspend fun deleteById(id: String)

}