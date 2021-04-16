package com.nativeboys.password.manager.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.nativeboys.password.manager.data.preferences.SortOrder
import com.nativeboys.password.manager.data.repository.ItemRepository

class SettingsBottomViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository
): ViewModel() {

    val itemSettings = itemRepository.findItemSettingsAsFlow().asLiveData()

    fun updateSortOrderAndFavoritesVisibility(order: SortOrder, hide: Boolean) = liveData {
        emit(itemRepository.updateSortOrderAndFavoritesVisibility(order, hide))
    }

}