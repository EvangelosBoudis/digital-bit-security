package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.nativeboys.password.manager.data.Result
import com.nativeboys.password.manager.data.repositories.items.ItemRepository
import java.lang.Exception

class ItemOverviewViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val itemId: String? = state.get<String>("item_id")

    val itemFieldsContent = liveData {
        val item = itemId?.let {
            itemRepository.findItemFieldsContentById(it)
        }
        emit(item)
    }

    fun toggleItemFavorite() = liveData {
        val success = itemId?.let {
            itemRepository.toggleItemFavorite(it)
        } ?: false
        emit(success)
    }

    fun deleteItem() = liveData {
        try {
            emit(Result.Success(itemRepository.deleteItemById(itemId!!)))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

}