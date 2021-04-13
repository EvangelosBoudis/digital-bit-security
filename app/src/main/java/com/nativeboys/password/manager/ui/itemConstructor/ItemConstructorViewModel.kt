package com.nativeboys.password.manager.ui.itemConstructor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class ItemConstructorViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val itemId: String? = state.get<String>("item_id")
    val thumbnails: MutableStateFlow<List<ThumbnailDto>> = MutableStateFlow(emptyList())

    val itemFieldsContent = liveData {
        val id = itemId ?: ""
        emit(itemRepository.findItemFieldsContentById(id))
    }

    init {
        itemId?.let { id ->
            viewModelScope.launch {
                thumbnails.value = itemRepository.findAllThumbnailsDto(id)
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Thumbnails
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun addThumbnail(url: String, index: Int = -1): Int {
        val currentThumbnails = thumbnails.value.toMutableList()
        currentThumbnails.add(
            if (index == -1) currentThumbnails.size - 1 else index,
            ThumbnailDto(UUID.randomUUID().toString(), url, true, 1)
        )
        thumbnails.value = currentThumbnails
        return currentThumbnails.indexOfFirst { it.url == url }
    }

    private fun updateThumbnail(thumbnailDto: ThumbnailDto, url: String): Int {
        val thumbnails = this.thumbnails.value
        val index = thumbnails.indexOf(thumbnailDto)
        this.thumbnails.value = thumbnails.map { thumbnail ->
            if (thumbnail.id == thumbnailDto.id) thumbnail.copy(url = url) else thumbnail
        }
        return index
    }

    fun deleteThumbnail(thumbnailId: String) {
        thumbnails.value = thumbnails.value.filter {
            it.deletable && it.id == thumbnailId
        }
    }

    fun selectThumbnail(thumbnailId: String) {
        thumbnails.value = thumbnails.value.map {
            if (it.type == 3) it else it.copy(type = if (it.id == thumbnailId) 2 else 1)
        }
    }

    private fun selectThumbnail(index: Int) {
        thumbnails.value = thumbnails.value.mapIndexed { thumbnailIndex, thumbnail ->
            if (thumbnail.type == 3) thumbnail else thumbnail.copy(type = if (index == thumbnailIndex) 2 else 1)
        }
    }

    fun addAndSelectThumbnail(url: String) {
        val index = addThumbnail(url)
        selectThumbnail(index)
    }

    fun updateAndSelectThumbnail(thumbnailDto: ThumbnailDto, url: String) {
        val index = updateThumbnail(thumbnailDto, url)
        selectThumbnail(index)
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Tags
    //////////////////////////////////////////////////////////////////////////////////////////

    fun addTag(name: String) {
        // TODO: implement
    }

    fun updateTag(tagDto: TagDto, name: String) {
        // TODO: implement
    }

    fun deleteTag(tagDto: TagDto) {
        // TODO: implement
    }

}