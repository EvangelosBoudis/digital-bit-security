package com.nativeboys.password.manager.ui.categoryConstructor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.repository.CategoryRepository

class CategoryConstructorViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val categoryId: String? = state.get<String>("category_id")

    val categoryWithFields = liveData {
        val id = categoryId ?: ""
        emit(if (id.isNotEmpty()) categoryRepository.findCategoryWithFieldsById(id) else null)
    }

}