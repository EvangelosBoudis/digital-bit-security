package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.ItemData
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.data.ItemWithContents
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items WHERE items.id == :id")
    suspend fun findItemWithContentById(id: String): ItemWithContents

    // Projection
    @Query("""
        SELECT items.id AS itemId, items.name AS itemName, items.description AS itemDescription, items.category_id AS itemCategoryId, thumbnails.url AS thumbnailUrl, items.date_modified AS lastModificationDate, items.favorite AS favoriteItem
        FROM items
        LEFT JOIN thumbnails ON thumbnails.id = items.thumbnail_id
        GROUP BY itemId
        """)
    fun findAllDtoAsFlow(): Flow<List<ItemDto>>

    @Query("""
        SELECT items.id AS itemId, items.name AS itemName, items.description AS itemDescription, items.category_id AS itemCategoryId, thumbnails.url AS thumbnailUrl, items.date_modified AS lastModificationDate, items.favorite AS favoriteItem
        FROM (SELECT * FROM items AS nested WHERE (nested.name LIKE '%' || :searchKey || '%' OR nested.tags LIKE '%' || :searchKey || '%')) AS items
        LEFT JOIN thumbnails ON thumbnails.id = items.thumbnail_id
        GROUP BY itemId
        ORDER BY itemName
        """)
    suspend fun findAllDtoByNameAndTagsSortedByNameAsFlow(searchKey: String): List<ItemDto>

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

    @Query("UPDATE items SET favorite = :favorite WHERE id = :id")
    suspend fun updateFavoriteItem(id: String, favorite: Boolean)

    @Update
    suspend fun update(item: ItemData)

    @Delete
    suspend fun delete(item: ItemData)

    @Query("DELETE FROM items WHERE items.id == :id")
    suspend fun deleteById(id: String)

}