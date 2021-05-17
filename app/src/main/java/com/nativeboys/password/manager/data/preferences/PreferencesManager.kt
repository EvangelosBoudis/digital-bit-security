package com.nativeboys.password.manager.data.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {

    fun observeSelectedCategoryId(): Flow<String>

    fun observeItemsSortOrder(): Flow<SortOrder>

    fun observeNonFavoritesVisibility(): Flow<Boolean>

    fun observeItemSearchKey(): Flow<String>

    fun observeDarkTheme(): Flow<Boolean>

    suspend fun updateSelectedCategoryId(id: String)

    suspend fun updateItemsSortOrder(order: SortOrder)

    suspend fun updateNonFavoriteItemsVisibility(hide: Boolean)

    suspend fun updateItemSearchKey(searchKey: String)

    suspend fun updateDarkTheme(enabled: Boolean)

}