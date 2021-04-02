package com.nativeboys.password.manager.data.repository

import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.data.ItemFieldsContentDto
import com.nativeboys.password.manager.data.local.FieldDao
import com.nativeboys.password.manager.data.local.ItemDao
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
    private val preferences: PreferencesManager
) {

    fun findItemsFilteredBySelectedCategoryAsFlow(): Flow<List<ItemDto>> {
        return combine(
            itemDao.findAllDtoAsFlow(),
            preferences.findSelectedCategoryIdAsFlow()
        ) { items, categoryId ->
            if (categoryId.isEmpty()) items
            else items.filter { it.itemCategoryId == categoryId }
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
        return ItemFieldsContentDto(itemWithContent.item, fieldContents)
    }

    suspend fun deleteItemById(id: String) = itemDao.deleteById(id)

    suspend fun updateItemsSortOrder(order: SortOrder) = preferences.updateItemsSortOrder(order)

    suspend fun updateNonFavoriteItemsVisibility(hide: Boolean) = preferences.updateNonFavoriteItemsVisibility(hide)

    suspend fun updateItemSearchKey(searchKey: String = "") = preferences.updateItemSearchKey(searchKey)

}