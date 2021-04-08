package com.nativeboys.password.manager.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.*
import com.nativeboys.password.manager.BuildConfig.DATA_STORE_PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferencesManager"

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {

    private val dataStore = context.createDataStore(DATA_STORE_PREFERENCES_NAME)

    private fun getPreferencesAsFlow(): Flow<Preferences> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                Log.i(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
    }

    fun findSelectedCategoryIdAsFlow() = getPreferencesAsFlow().map {
        it[PreferencesKeys.SELECTED_CATEGORY_ID] ?: ""
    }

    fun findItemsSortOrderAsFlow() = getPreferencesAsFlow().map {
        SortOrder.valueOf(it[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_NAME.name)
    }

    fun areNonFavoritesInvisible() = getPreferencesAsFlow().map {
        it[PreferencesKeys.HIDE_NON_FAVORITES] ?: false
    }

    fun findItemSearchKey() = getPreferencesAsFlow().map {
        it[PreferencesKeys.ITEM_SEARCH_KEY] ?: ""
    }

    suspend fun updateItemsSortOrder(order: SortOrder) = updatePreferences(PreferencesKeys.SORT_ORDER, order.name)

    suspend fun updateNonFavoriteItemsVisibility(hide: Boolean) = updatePreferences(PreferencesKeys.HIDE_NON_FAVORITES, hide)

    suspend fun updateSelectedCategoryId(id: String) = updatePreferences(PreferencesKeys.SELECTED_CATEGORY_ID, id)

    suspend fun updateItemSearchKey(searchKey: String) = updatePreferences(PreferencesKeys.ITEM_SEARCH_KEY, searchKey)

    private suspend fun <T> updatePreferences(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
        val HIDE_NON_FAVORITES = preferencesKey<Boolean>("hide_non_favorites")
        val SELECTED_CATEGORY_ID = preferencesKey<String>("selected_category_id")
        val ITEM_SEARCH_KEY = preferencesKey<String>("item_search_key")
    }

}