package com.nativeboys.password.manager.data.repository

import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.data.ItemFieldsContentDto
import com.nativeboys.password.manager.data.local.FieldDao
import com.nativeboys.password.manager.data.local.ItemDao
import com.nativeboys.password.manager.data.local.ThumbnailDao
import com.nativeboys.password.manager.data.preferences.ItemSettings
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.data.preferences.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val fieldDao: FieldDao,
    private val thumbnailDao: ThumbnailDao,
    private val preferences: PreferencesManager
) {

    fun findItemsDtoFilteredAndSortedAsFlow(): Flow<List<ItemDto>> {
        return combine(
            itemDao.findAllDtoAsFlow(),
            preferences.findSelectedCategoryIdAsFlow(),
            preferences.findItemsSortOrderAsFlow(),
            preferences.areNonFavoritesInvisible()
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

    // ALERT! -> This Flow triggered searchKey changes and then it queries the database [Different from findItemsFilteredBySelectedCategoryAsFlow()]
    fun findItemsFilteredBySearchKeyAsFlow() =
        preferences.findItemSearchKey().map { searchKey ->
            itemDao.findAllDtoByNameAndTagsSortedByNameAsFlow(searchKey.trim { it <= ' ' })
        }

    suspend fun findItemFieldsContentById(id: String): ItemFieldsContentDto {
        val itemWithContent = itemDao.findItemWithContentById(id)
        val fields = fieldDao.findByIds(itemWithContent.contents.map { it.fieldId })
        val fieldContents = mutableListOf<FieldContentDto>()
        for (content in itemWithContent.contents) {
            val field = fields.firstOrNull {
                content.fieldId == it.id
            } ?: continue
            fieldContents.add(FieldContentDto(content.id, content.content, field.name, field.type))
        }
        return ItemFieldsContentDto(
            itemWithContent.item,
            fieldContents,
            thumbnailDao.findById(itemWithContent.item.thumbnailId).url
        )
    }

    fun findItemSettingsAsFlow() = combine(
            preferences.findItemsSortOrderAsFlow(),
            preferences.areNonFavoritesInvisible())
        { sortOrder, nonFavoritesInvisible ->
            ItemSettings(sortOrder, nonFavoritesInvisible)
        }

    suspend fun deleteItemById(id: String) = itemDao.deleteById(id)

    suspend fun toggleItemFavorite(itemId: String): Boolean {
        val favorite = !itemDao.findById(itemId).favorite
        itemDao.updateFavoriteItem(itemId, favorite)
        return favorite
    }

    private suspend fun updateItemsSortOrder(order: SortOrder) = preferences.updateItemsSortOrder(order)

    private suspend fun updateNonFavoriteItemsVisibility(hide: Boolean) = preferences.updateNonFavoriteItemsVisibility(hide)

    suspend fun updateSortOrderAndFavoritesVisibility(order: SortOrder, hide: Boolean) {
        updateItemsSortOrder(order)
        updateNonFavoriteItemsVisibility(hide)
    }

    suspend fun updateItemSearchKey(searchKey: String = "") = preferences.updateItemSearchKey(searchKey)

}