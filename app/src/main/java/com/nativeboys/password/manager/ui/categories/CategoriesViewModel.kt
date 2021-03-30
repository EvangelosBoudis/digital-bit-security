package com.nativeboys.password.manager.ui.categories

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nativeboys.password.manager.data.CategoryDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class CategoriesViewModel @ViewModelInject constructor(
    private val categoryDao: CategoryDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val searchKey = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_NAME)

    private val categoriesFlow =
        combine(searchKey, sortOrder) { key, order ->
            Pair(key, order)
        }.flatMapLatest { (key, order) ->
            if (order == SortOrder.BY_NAME) categoryDao.findByNameSortedByName(key)
            else categoryDao.findByNameSortedByDateModified(key)
        }

    val categories = categoriesFlow.asLiveData()

}

enum class SortOrder { BY_NAME, BY_DATE }