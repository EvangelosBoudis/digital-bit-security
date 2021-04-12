package com.nativeboys.password.manager.ui.itemConstructor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nativeboys.password.manager.data.repository.ItemRepository

class ItemConstructorViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val itemId: String? = state.get<String>("item_id")

    val itemFieldsContent = liveData {
        val id = itemId ?: ""
        emit(itemRepository.findItemFieldsContentById(id))
    }

    val thumbnails = liveData {
        val id = itemId ?: ""
        emit(itemRepository.findAllThumbnailsDto(id))
    }

}