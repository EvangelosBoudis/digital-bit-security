package com.nativeboys.password.manager.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.preferences.SortOrder
import com.nativeboys.password.manager.data.repository.CategoryRepository
import com.nativeboys.password.manager.data.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    val categories = categoryRepository.findAllCategoriesDtoAsFlow().asLiveData()

    val itemsDto = itemRepository.findItemsFilteredBySelectedCategoryAsFlow().asLiveData()

    fun setSelectedCategory(id: String) = viewModelScope.launch(context = Dispatchers.IO) {
        categoryRepository.updateSelectedCategoryId(id)
    }

    fun setItemsSortOrder(order: SortOrder) = viewModelScope.launch(context = Dispatchers.IO) {
        itemRepository.updateItemsSortOrder(order)
    }

    fun deleteItem(id: String) = viewModelScope.launch(context = Dispatchers.IO) {
        itemRepository.deleteItemById(id)
    }

}