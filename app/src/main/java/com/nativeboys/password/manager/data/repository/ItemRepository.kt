package com.nativeboys.password.manager.data.repository

import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.data.local.ItemDao
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.data.preferences.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val preferences: PreferencesManager
) {

    fun findAllItemsDtoAsFlow(): Flow<List<ItemDto>> {
        return combine(itemDao.findAllDtoAsFlow(), preferences.findSelectedCategoryIdAsFlow()) { items, categoryId ->
            if (categoryId.isEmpty()) items
            else items.filter { it.itemCategoryId == categoryId }
        }
    }

    suspend fun updateItemsSortOrder(order: SortOrder) = preferences.updateItemsSortOrder(order)

    suspend fun updateNonFavoriteItemsVisibility(hide: Boolean) = preferences.updateNonFavoriteItemsVisibility(hide)

    suspend fun deleteItemById(id: String) = itemDao.deleteById(id)

}