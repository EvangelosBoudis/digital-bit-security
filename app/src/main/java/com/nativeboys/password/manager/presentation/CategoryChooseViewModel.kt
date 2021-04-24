package com.nativeboys.password.manager.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nativeboys.password.manager.data.repository.CategoryRepository

class CategoryChooseViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val categories = liveData {
        emit(categoryRepository.findAllCategories())
    }

/*
    val searchKey = MutableStateFlow("")
    val sortOrder = MutableStateFlow(0)

    private val categoriesFlow =
        combine(searchKey, sortOrder) { key, order ->
            Pair(key, order)
        }.flatMapLatest { (key, order) ->
            categoryRepository.findAllCategoriesByNameAsFlow(key, order)
        }*/

}