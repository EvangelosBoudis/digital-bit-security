package com.nativeboys.password.manager.data.repositories.items

import com.nativeboys.password.manager.data.*
import com.nativeboys.password.manager.data.preferences.ItemSettings
import com.nativeboys.password.manager.data.preferences.SortOrder
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun countAllItemsWithThumbnailId(thumbnailId: String): Int

    suspend fun findItemById(id: String): ItemData

    suspend fun findItemFieldsContentById(id: String): ItemFieldsContentDto

    suspend fun findAllThumbnailsDto(selectedItemId: String, addDefault: Boolean = true): List<ThumbnailDto>

    fun observeItemsDtoFilteredAndSorted(): Flow<List<ItemDto>>

    fun observeItemsFilteredBySearchKey(): Flow<List<ItemDto>>

    fun observeItemSettings(): Flow<ItemSettings>

    suspend fun deleteItemById(id: String)

    suspend fun toggleItemFavorite(itemId: String): Boolean

    suspend fun saveOrUpdateItem(id: String?, name: String, description: String, notes: String?, tags: String?, favorite: Boolean,
                                 requiresPassword: Boolean, categoryId: String, thumbnailId: String, fieldsContent: List<FieldContentDto>)

    suspend fun updateItemSearchKey(searchKey: String = "")

    suspend fun updateSortOrderAndFavoritesVisibility(order: SortOrder, hide: Boolean)

}