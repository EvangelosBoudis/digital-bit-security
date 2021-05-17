package com.nativeboys.password.manager.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.*
import com.nativeboys.password.manager.BuildConfig.DATA_STORE_PREFERENCES_NAME
import com.nativeboys.password.manager.data.preferences.PreferencesManagerImpl.PreferencesKeys.DARK_THEME_ENABLED
import com.nativeboys.password.manager.data.preferences.PreferencesManagerImpl.PreferencesKeys.HIDE_NON_FAVORITES
import com.nativeboys.password.manager.data.preferences.PreferencesManagerImpl.PreferencesKeys.ITEM_SEARCH_KEY
import com.nativeboys.password.manager.data.preferences.PreferencesManagerImpl.PreferencesKeys.SELECTED_CATEGORY_ID
import com.nativeboys.password.manager.data.preferences.PreferencesManagerImpl.PreferencesKeys.SORT_ORDER
import com.nativeboys.password.manager.data.preferences.PreferencesManagerImpl.PreferencesKeys.TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext context: Context
): PreferencesManager {

    private val dataStore = context.createDataStore(DATA_STORE_PREFERENCES_NAME)

    private fun observePreferences(): Flow<Preferences> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.i(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
    }

    private suspend fun <T> update(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private suspend fun <T> read(key: Preferences.Key<T>): T? {
        val preferences = dataStore.data.firstOrNull()
        return preferences?.let { it[key] }
    }

    override fun observeSelectedCategoryId(): Flow<String> {
        return observePreferences().map {
            it[SELECTED_CATEGORY_ID] ?: ""
        }
    }

    override fun observeItemsSortOrder(): Flow<SortOrder> {
        return observePreferences().map {
            SortOrder.valueOf(it[SORT_ORDER] ?: SortOrder.BY_NAME.name)
        }
    }

    override fun observeNonFavoritesVisibility(): Flow<Boolean> {
        return observePreferences().map {
            it[HIDE_NON_FAVORITES] ?: false
        }
    }

    override fun observeItemSearchKey(): Flow<String> {
        return observePreferences().map {
            it[ITEM_SEARCH_KEY] ?: ""
        }
    }

    override fun observeDarkTheme(): Flow<Boolean> {
        return observePreferences().map {
            it[DARK_THEME_ENABLED] ?: false
        }
    }

    override suspend fun updateSelectedCategoryId(id: String) {
        update(SELECTED_CATEGORY_ID, id)
    }

    override suspend fun updateItemsSortOrder(order: SortOrder) {
        update(SORT_ORDER, order.name)
    }

    override suspend fun updateNonFavoriteItemsVisibility(hide: Boolean) {
        update(HIDE_NON_FAVORITES, hide)
    }

    override suspend fun updateItemSearchKey(searchKey: String) {
        update(ITEM_SEARCH_KEY, searchKey)
    }

    override suspend fun updateDarkTheme(enabled: Boolean) {
        update(DARK_THEME_ENABLED, enabled)
    }

    private object PreferencesKeys {

        const val TAG = "PreferencesManager"

        val SORT_ORDER = preferencesKey<String>("sort_order")
        val HIDE_NON_FAVORITES = preferencesKey<Boolean>("hide_non_favorites")
        val SELECTED_CATEGORY_ID = preferencesKey<String>("selected_category_id")
        val ITEM_SEARCH_KEY = preferencesKey<String>("item_search_key")
        val DARK_THEME_ENABLED = preferencesKey<Boolean>("dark_theme_enabled")
    }

}