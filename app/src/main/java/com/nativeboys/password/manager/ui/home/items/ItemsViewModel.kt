package com.nativeboys.password.manager.ui.home.items

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nativeboys.password.manager.data.repository.CategoryRepository
import com.nativeboys.password.manager.data.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository
): ViewModel() {

    val categories = categoryRepository.findAllCategoriesDtoAsFlow().asLiveData()

    val itemsDto = itemRepository.findItemsDtoFilteredAndSortedAsFlow().asLiveData()

    fun setSelectedCategory(id: String) = viewModelScope.launch(context = Dispatchers.IO) {
        categoryRepository.updateSelectedCategoryId(id)
    }

    fun deleteItem(id: String) = viewModelScope.launch(context = Dispatchers.IO) {
        itemRepository.deleteItemById(id)
    }

}