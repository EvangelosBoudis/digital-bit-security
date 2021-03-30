package com.nativeboys.password.manager.ui.categoryConstructor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.nativeboys.password.manager.data.CategoryDao
import com.nativeboys.password.manager.data.CategoryEntity
import com.nativeboys.password.manager.data.FieldDao
import com.nativeboys.password.manager.data.FieldEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class CategoryConstructorViewModel @ViewModelInject constructor(
    private val categoryDao: CategoryDao,
    private val fieldDao: FieldDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val categoryId: String? = state.get<String>("category_id")
    private val categoryIdFlow = MutableStateFlow(categoryId)

    private val categoryFlow: Flow<CategoryEntity?> = categoryIdFlow.map { categoryId ->
        categoryId?.let { categoryDao.findById(it) }
    }

    val category = categoryFlow.asLiveData()

    private val fieldsFlow: Flow<List<FieldEntity>> = categoryIdFlow.map { categoryId ->
        categoryId?.let { fieldDao.findByCategoryId(it) } ?: emptyList()
    }

    val fields = fieldsFlow.asLiveData()

}