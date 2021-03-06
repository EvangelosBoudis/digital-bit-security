package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.repositories.category.CategoryRepository
import com.nativeboys.password.manager.data.repositories.items.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    private val categoryRepository: CategoryRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val categories = categoryRepository.observeAllCategoriesDto().asLiveData()

    val itemsDto = itemRepository.observeItemsDtoFilteredAndSorted().asLiveData()

    val emptyData = itemsDto.map { items ->
        items.isEmpty()
    }

    fun setSelectedCategory(id: String) = viewModelScope.launch(context = Dispatchers.IO) {
        categoryRepository.updateSelectedCategoryId(id)
    }

    fun deleteItem() = viewModelScope.launch(context = Dispatchers.IO) {
        val itemId: String? = state[PENDING_ITEM_TO_DELETE]
        itemId?.let { id ->
            itemRepository.deleteItemById(id)
        }
    }

    fun setPendingItemToDelete(itemId: String) {
        state[PENDING_ITEM_TO_DELETE] = itemId
    }

    companion object {
        const val PENDING_ITEM_TO_DELETE = "PENDING_ITEM_TO_DELETE"
    }

}