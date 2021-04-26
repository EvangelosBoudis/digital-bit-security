package com.nativeboys.password.manager.data.repository

import androidx.room.withTransaction
import com.nativeboys.password.manager.data.*
import com.nativeboys.password.manager.data.local.*
import com.nativeboys.password.manager.data.preferences.ItemSettings
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.data.preferences.SortOrder
import com.nativeboys.password.manager.util.toComparable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val database: AppDatabase,
    private val itemDao: ItemDao,
    private val fieldDao: FieldDao,
    private val contentDao: ContentDao,
    private val thumbnailDao: ThumbnailDao,
    private val preferences: PreferencesManager
) {

    //////////////////////////////////////////////////////////////////////////////////////////
    /// SQLite
    //////////////////////////////////////////////////////////////////////////////////////////

    suspend fun findItemById(id: String) = itemDao.findById(id)

    suspend fun getItemsCountWithThumbnailId(thumbnailId: String) = itemDao.getCountWithThumbnailId(thumbnailId)

    fun observeItemsDtoFilteredAndSorted(): Flow<List<ItemDto>> {
        return combine(
            itemDao.observeAllDto(),
            preferences.observeSelectedCategoryId(),
            preferences.observeItemsSortOrder(),
            preferences.observeNonFavoritesVisibility()
        ) { items, categoryId, sortOrder, nonFavoritesInvisible ->
            // Filter & Sort programmatically and not at query level because we have to get notified about item, selected category, and filter changes
            val filteredItems = items.filter {
                if (nonFavoritesInvisible && !it.favoriteItem) return@filter false
                if (categoryId.isNotEmpty() && it.itemCategoryId != categoryId) return@filter false
                true
            }
            return@combine if (sortOrder == SortOrder.BY_NAME) filteredItems.sortedBy { it.itemName }
            else filteredItems.sortedByDescending { it.lastModificationDate }
        }
    }

    fun observeItemsFilteredBySearchKey(): Flow<List<ItemDto>> {
        return combine(
            itemDao.observeAllDtoSortedByName(),
            preferences.observeItemSearchKey()
        ) { items, searchKey ->
            val comparableSearchKey = searchKey.toComparable()
            items.filter {
                val comparableName = it.itemName.toComparable()
                val comparableTags = it.itemTags?.toComparable()
                comparableName.contains(comparableSearchKey) || (comparableTags?.contains(comparableSearchKey) ?: false)
            }
        }
    }

    suspend fun findItemFieldsContentById(id: String): ItemFieldsContentDto {
        val item = itemDao.findById(id)
        val thumbnailUrl = thumbnailDao.findById(item.thumbnailId).url
        val fieldsContent = fieldDao.findAllDtoByCategoryIdAndItemId(item.categoryId, item.id)
        return ItemFieldsContentDto(
            item,
            fieldsContent,
            thumbnailUrl
        )
    }

    fun observeItemSettings() = combine(
            preferences.observeItemsSortOrder(),
            preferences.observeNonFavoritesVisibility())
        { sortOrder, nonFavoritesInvisible ->
            ItemSettings(sortOrder, nonFavoritesInvisible)
        }

    suspend fun deleteItemById(id: String) = itemDao.deleteById(id)

    suspend fun toggleItemFavorite(itemId: String): Boolean {
        val favorite = !itemDao.findById(itemId).favorite
        itemDao.updateFavoriteItem(itemId, favorite)
        return favorite
    }

    suspend fun findAllThumbnailsDto(selectedItemId: String, addDefault: Boolean = true): List<ThumbnailDto> {
        val allItems = itemDao.findAll()
        val selectedItem = allItems.firstOrNull { item ->
            item.id == selectedItemId
        }
        val thumbnails = thumbnailDao.findAll()
            .map { thumbnail ->
            val deletable = allItems
                .filter { item -> item.thumbnailId == thumbnail.id }
                .size <= 1
            ThumbnailDto(thumbnail, deletable, if (selectedItem?.thumbnailId == thumbnail.id) 2 else 1)
        }.toMutableList()
        if (addDefault) thumbnails.add(ThumbnailDto())
        return thumbnails
    }

    suspend fun saveOrUpdateItem(
        id: String?,
        name: String,
        description: String,
        notes: String?,
        tags: String?,
        favorite: Boolean,
        requiresPassword: Boolean,
        categoryId: String,
        thumbnailId: String,
        fieldsContent: List<FieldContentDto>
    ) {

        val userId = UUID.randomUUID().toString() // TODO: Change
        val itemId = id ?: UUID.randomUUID().toString()

        val contents = fieldsContent
            .map { ContentData(fieldId = it.fieldId, itemId = itemId, content = it.textContent ?: "") }

        val item = ItemData(
            itemId, name,
            description, notes,
            tags, favorite,
            thumbnailId, Date(),
            requiresPassword,
            categoryId,
            userId
        )

        database.withTransaction {
            if (id == null) itemDao.save(item) else itemDao.update(item)
            contentDao.deleteAllByItemId(itemId)
            contentDao.save(contents)
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Preference Data Store
    //////////////////////////////////////////////////////////////////////////////////////////

    private suspend fun updateItemsSortOrder(order: SortOrder) = preferences.updateItemsSortOrder(order)

    private suspend fun updateNonFavoriteItemsVisibility(hide: Boolean) = preferences.updateNonFavoriteItemsVisibility(hide)

    suspend fun updateItemSearchKey(searchKey: String = "") = preferences.updateItemSearchKey(searchKey)

    suspend fun updateSortOrderAndFavoritesVisibility(order: SortOrder, hide: Boolean) {
        updateItemsSortOrder(order)
        updateNonFavoriteItemsVisibility(hide)
    }

}