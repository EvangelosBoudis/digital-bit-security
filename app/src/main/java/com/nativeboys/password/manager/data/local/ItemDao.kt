package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.ItemData
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.data.ItemWithContents
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT COUNT(id) FROM items WHERE thumbnail_id = :thumbnailId")
    suspend fun getCountWithThumbnailId(thumbnailId: String): Int

    @Query("SELECT * FROM items WHERE items.id == :id")
    suspend fun findItemWithContentById(id: String): ItemWithContents

    @Query("""
        SELECT mItems.id AS itemId, mItems.name AS itemName, mItems.description AS itemDescription, mItems.tags AS itemTags, mItems.favorite AS favoriteItem, mItems.category_id AS itemCategoryId, mItems.date_modified AS lastModificationDate, thumbnails.url AS thumbnailUrl, mItems.requires_password AS requestPassword
        FROM (SELECT * FROM items WHERE items.id = :id) AS mItems
        LEFT JOIN thumbnails ON thumbnails.id = mItems.thumbnail_id
        """)
    suspend fun findDtoByItemId(id: String): ItemDto

    // Projection
    @Query(
        """
        SELECT items.id AS itemId, items.name AS itemName, items.description AS itemDescription, items.tags AS itemTags, items.favorite AS favoriteItem, items.category_id AS itemCategoryId, items.date_modified AS lastModificationDate, thumbnails.url AS thumbnailUrl, items.requires_password AS requestPassword
        FROM items
        LEFT JOIN thumbnails ON thumbnails.id = items.thumbnail_id
        GROUP BY itemId
        """
    )
    fun observeAllDto(): Flow<List<ItemDto>>

    @Query(
        """
        SELECT items.id AS itemId, items.name AS itemName, items.description AS itemDescription, items.tags AS itemTags, items.favorite AS favoriteItem, items.category_id AS itemCategoryId, items.date_modified AS lastModificationDate, thumbnails.url AS thumbnailUrl, items.requires_password AS requestPassword
        FROM items
        LEFT JOIN thumbnails ON thumbnails.id = items.thumbnail_id
        GROUP BY itemId
        ORDER BY itemName
        """
    )
    fun observeAllDtoSortedByName(): Flow<List<ItemDto>>

    @Query(
        """
        SELECT items.id AS itemId, items.name AS itemName, items.description AS itemDescription, items.tags AS itemTags, items.favorite AS favoriteItem, items.category_id AS itemCategoryId, items.date_modified AS lastModificationDate, thumbnails.url AS thumbnailUrl, items.requires_password AS requestPassword
        FROM (SELECT * FROM items AS nested WHERE (nested.name LIKE '%' || :searchKey || '%' OR nested.tags LIKE '%' || :searchKey || '%')) AS items
        LEFT JOIN thumbnails ON thumbnails.id = items.thumbnail_id
        GROUP BY itemId
        ORDER BY itemName
        """
    )
    suspend fun findAllDtoByNameAndTagsSortedByName(searchKey: String): List<ItemDto>

    @Query("SELECT * FROM items WHERE id == :id")
    suspend fun findById(id: String): ItemData

    @Query("SELECT * FROM items")
    suspend fun findAll(): List<ItemData>

    @Query("SELECT * FROM items WHERE category_id == :categoryId")
    fun observeAllByCategoryId(categoryId: String): Flow<List<ItemData>>

    @Query("SELECT * FROM items WHERE favorite == :favorite")
    fun observeAllFavorites(favorite: Boolean = true): Flow<List<ItemData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: ItemData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(items: List<ItemData>)

    @Query("UPDATE items SET favorite = :favorite WHERE id = :id")
    suspend fun updateFavoriteItem(id: String, favorite: Boolean)

    @Update
    suspend fun update(item: ItemData)

    @Delete
    @Transaction
    suspend fun delete(item: ItemData)

    @Transaction
    @Query("DELETE FROM items WHERE items.id == :id")
    suspend fun deleteById(id: String)

}