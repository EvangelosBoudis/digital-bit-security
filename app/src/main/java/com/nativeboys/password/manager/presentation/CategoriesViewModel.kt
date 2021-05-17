package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.Result
import com.nativeboys.password.manager.data.repositories.category.CategoryRepository
import java.lang.Exception

class CategoriesViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val categories = categoryRepository.observeAllCategories().asLiveData()

    fun deleteCategory() = liveData {
        try {
            val categoryId: String? = state[PENDING_CATEGORY_TO_DELETE]
            emit(Result.Success(categoryRepository.deleteCategoryById(categoryId!!)))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    fun setPendingCategoryToDelete(categoryId: String) {
        state[PENDING_CATEGORY_TO_DELETE] = categoryId
    }

    companion object {
        const val PENDING_CATEGORY_TO_DELETE = "PENDING_CATEGORY_TO_DELETE"
    }

}