package com.nativeboys.password.manager.data.repository

import androidx.room.Transaction
import com.nativeboys.password.manager.data.*
import com.nativeboys.password.manager.data.local.ContentDao
import com.nativeboys.password.manager.data.local.FieldDao
import com.nativeboys.password.manager.data.local.ItemDao
import com.nativeboys.password.manager.data.local.ThumbnailDao
import com.nativeboys.password.manager.data.preferences.ItemSettings
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.data.preferences.SortOrder
import com.nativeboys.password.manager.presentation.ItemConstructorViewModel.Companion.DESCRIPTION_ID
import com.nativeboys.password.manager.presentation.ItemConstructorViewModel.Companion.NAME_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val fieldDao: FieldDao,
    private val contentDao: ContentDao,
    private val thumbnailDao: ThumbnailDao,
    private val preferences: PreferencesManager
) {

    //////////////////////////////////////////////////////////////////////////////////////////
    /// SQLite
    //////////////////////////////////////////////////////////////////////////////////////////

    suspend fun saveItem(item: ItemData) = itemDao.save(item)

    suspend fun updateItem(item: ItemData) = itemDao.update(item)

    suspend fun findItemById(id: String) = itemDao.findById(id)

    suspend fun getItemsCountWithThumbnailId(thumbnailId: String) = itemDao.getCountWithThumbnailId(thumbnailId)

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
            fieldContents.add(FieldContentDto(content.id, content.content, field.id, field.name, field.type))
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

    suspend fun findAllThumbnailsDto(selectedItemId: String, addDefault: Boolean = true): List<ThumbnailDto> {
        val allItems = itemDao.findAll()
        val selectedItem = allItems.firstOrNull { item ->
            item.id == selectedItemId
        }
        val thumbnails = thumbnailDao.findAll().map { thumbnail ->
            val itemsWithSameThumbnailSize = allItems.filter { item ->
                item.thumbnailId == thumbnail.id
            }.size
            ThumbnailDto(thumbnail, itemsWithSameThumbnailSize <= 1, if (selectedItem?.thumbnailId == thumbnail.id) 2 else 1)
        }.toMutableList()
        if (addDefault) thumbnails.add(ThumbnailDto())
        return thumbnails
    }

    private suspend fun replaceAllFieldContent(
        itemId: String,
        contents: List<ContentData>
    ) {
        val prevContents = contentDao.findAllByItemId(itemId)
        contentDao.delete(prevContents)
        contentDao.save(contents)
    }

    @Transaction
    suspend fun updateItem(
        id: String,
        categoryId: String,
        thumbnailId: String,
        notes: String?,
        favorite: Boolean,
        passwordIsRequired: Boolean,
        tagsDto: List<TagDto>,
        fieldsContentDto: List<FieldContentDto>
    ) {

        val userId = UUID.randomUUID().toString() // TODO: Change

        val tags = tagsDto
            .filter { it.name.isNotEmpty() }
            .joinToString(",") { it.name }

        val name = fieldsContentDto
            .firstOrNull { it.contentId == NAME_ID }?.textContent ?: ""

        val description = fieldsContentDto
            .firstOrNull { it.contentId == DESCRIPTION_ID }?.textContent ?: ""

        val contents = fieldsContentDto
            .filter { it.contentId != NAME_ID && it.contentId != DESCRIPTION_ID }
            .map { ContentData(fieldId = it.fieldId, itemId = id, content = it.textContent) }

        val item = ItemData(
            id, name, description,
            notes, tags,
            favorite, thumbnailId,
            Date(), passwordIsRequired,
            categoryId, userId
        )

        itemDao.update(item)
        replaceAllFieldContent(id, contents)
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