package com.nativeboys.password.manager.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nativeboys.password.manager.data.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchEngineViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository
): ViewModel() {

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            itemRepository.updateItemSearchKey()
        }
    }

    val items = itemRepository.findItemsFilteredBySearchKeyAsFlow().asLiveData()

    fun updateItemSearchKey(searchKey: String) = viewModelScope.launch(context = Dispatchers.IO) {
        itemRepository.updateItemSearchKey(searchKey)
    }

}