package com.nativeboys.password.manager.ui.categoryConstructor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.data.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class CategoryConstructorViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val categoryId: String? = state.get<String>("category_id")
    private val categoryIdFlow = MutableStateFlow(categoryId)

    private val categoryFlow: Flow<CategoryData?> = categoryIdFlow.map { categoryId ->
        categoryId?.let { categoryRepository.findCategoryById(it) }
    }

    val category = categoryFlow.asLiveData()

    private val fieldsFlow: Flow<List<FieldData>> = categoryIdFlow.map { categoryId ->
        categoryId?.let { categoryRepository.findFieldsByCategoryId(it) } ?: emptyList()
    }

    val fields = fieldsFlow.asLiveData()

}