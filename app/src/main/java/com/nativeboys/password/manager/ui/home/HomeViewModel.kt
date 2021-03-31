package com.nativeboys.password.manager.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.nativeboys.password.manager.data.repository.CategoryRepository
import com.nativeboys.password.manager.data.repository.ItemRepository
import kotlinx.coroutines.flow.combine

class HomeViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    private val selectedCategoryId = state.getLiveData<String>("category_id")

    private val categories = combine(selectedCategoryId.asFlow(), categoryRepository.findAllAsFlow()) { selectedId, categories ->

    }

}