package com.nativeboys.password.manager.ui.itemOverview

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nativeboys.password.manager.data.repository.ItemRepository

class ItemOverviewViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    private val itemId: String? = state.get<String>("item_id")

    val itemFieldsContent = liveData {
        val id = itemId ?: ""
        emit(if (id.isNotEmpty()) itemRepository.findItemFieldsContentById(id) else null)
    }

    fun toggleItemFavorite() = liveData {
        emit(itemRepository.toggleItemFavorite(itemId ?: ""))
    }

}