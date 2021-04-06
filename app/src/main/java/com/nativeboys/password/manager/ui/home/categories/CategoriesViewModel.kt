package com.nativeboys.password.manager.ui.home.categories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nativeboys.password.manager.data.repository.CategoryRepository

class CategoriesViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

/*    val searchKey = MutableStateFlow("")
    val sortOrder = MutableStateFlow(0)

    private val categoriesFlow =
        combine(searchKey, sortOrder) { key, order ->
            Pair(key, order)
        }.flatMapLatest { (key, order) ->
            categoryRepository.findAllCategoriesByNameAsFlow(key, order)
        }

    //val categories = categoryRepository.findAllCategories().asLiveData()*/

    val categories = liveData {
        emit(categoryRepository.findAllCategories())
    }

}