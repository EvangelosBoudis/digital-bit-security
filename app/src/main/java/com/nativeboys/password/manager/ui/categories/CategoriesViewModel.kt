package com.nativeboys.password.manager.ui.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nativeboys.password.manager.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class CategoriesViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val searchKey = MutableStateFlow("")
    val sortOrder = MutableStateFlow(0)

    private val categoriesFlow =
        combine(searchKey, sortOrder) { key, order ->
            Pair(key, order)
        }.flatMapLatest { (key, order) ->
            categoryRepository.findAllCategoriesByNameAsFlow(key, order)
        }

    val categories = categoriesFlow.asLiveData()

}

//enum class SortOrder { BY_NAME, BY_DATE }