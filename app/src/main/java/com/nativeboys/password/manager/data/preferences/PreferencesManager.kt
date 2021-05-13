package com.nativeboys.password.manager.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.*
import com.nativeboys.password.manager.BuildConfig.DATA_STORE_PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferencesManager"

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext context: Context
) {

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

    fun observeSelectedCategoryId() = observePreferences().map {
        it[PreferencesKeys.SELECTED_CATEGORY_ID] ?: ""
    }

    fun observeItemsSortOrder() = observePreferences().map {
        SortOrder.valueOf(it[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_NAME.name)
    }

    fun observeNonFavoritesVisibility() = observePreferences().map {
        it[PreferencesKeys.HIDE_NON_FAVORITES] ?: false
    }

    fun observeItemSearchKey() = observePreferences().map {
        it[PreferencesKeys.ITEM_SEARCH_KEY] ?: ""
    }

    fun observeDarkTheme() = observePreferences().map {
        it[PreferencesKeys.DARK_THEME_ENABLED] ?: false
    }

    fun observeRequestPassword() = observePreferences().map {
        it[PreferencesKeys.REQUEST_PASSWORD] ?: false
    }

    suspend fun updateItemsSortOrder(order: SortOrder) = update(PreferencesKeys.SORT_ORDER, order.name)

    suspend fun updateNonFavoriteItemsVisibility(hide: Boolean) = update(PreferencesKeys.HIDE_NON_FAVORITES, hide)

    suspend fun updateSelectedCategoryId(id: String) = update(PreferencesKeys.SELECTED_CATEGORY_ID, id)

    suspend fun updateItemSearchKey(searchKey: String) = update(PreferencesKeys.ITEM_SEARCH_KEY, searchKey)

    suspend fun updateDarkTheme(enabled: Boolean) = update(PreferencesKeys.DARK_THEME_ENABLED, enabled)

    suspend fun updateRequestPassword(request: Boolean) = update(PreferencesKeys.REQUEST_PASSWORD, request)

    suspend fun isDarkThemeEnabled() = read(PreferencesKeys.DARK_THEME_ENABLED)

    private suspend fun <T> update(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    private suspend fun <T> read(key: Preferences.Key<T>): T? {
        val preferences = dataStore.data.firstOrNull()
        return preferences?.let { it[key] }
    }

    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
        val HIDE_NON_FAVORITES = preferencesKey<Boolean>("hide_non_favorites")
        val SELECTED_CATEGORY_ID = preferencesKey<String>("selected_category_id")
        val ITEM_SEARCH_KEY = preferencesKey<String>("item_search_key")
        val DARK_THEME_ENABLED = preferencesKey<Boolean>("dark_theme_enabled")
        val REQUEST_PASSWORD = preferencesKey<Boolean>("request_password")
    }

}