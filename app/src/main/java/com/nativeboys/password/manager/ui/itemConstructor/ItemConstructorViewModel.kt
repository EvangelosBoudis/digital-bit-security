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
    val passwordIsRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val thumbnails: MutableStateFlow<List<ThumbnailDto>> = MutableStateFlow(emptyList())
    val tags: MutableStateFlow<List<TagDto>> = MutableStateFlow(emptyList())

    val itemFieldsContent = liveData {
        val id = itemId ?: ""
        emit(itemRepository.findItemFieldsContentById(id))
    }

    init {
        itemId?.let { id ->
            viewModelScope.launch {
                thumbnails.value = itemRepository.findAllThumbnailsDto(id)
                val item = itemRepository.findItemById(id)
                tags.value = item.tagsAsDto()
                passwordIsRequired.value = item.requiresPassword
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Thumbnails
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun addThumbnail(thumbnailUrl: String, index: Int = -1): Int {
        val thumbnails = this.thumbnails.value.toMutableList()
        thumbnails.add(
            if (index == -1) thumbnails.size - 1 else index,
            ThumbnailDto(UUID.randomUUID().toString(), thumbnailUrl, true, 1)
        )
        this.thumbnails.value = thumbnails
        return thumbnails.indexOfFirst { it.url == thumbnailUrl }
    }

    private fun updateThumbnailUrl(thumbnailId: String, thumbnailUrl: String): Int {
        val thumbnails = this.thumbnails.value
        val thumbnail = thumbnails.firstOrNull { it.id == thumbnailId }
        val index = thumbnail?.let { thumbnails.indexOf(it) } ?: -1
        thumbnail?.let { item ->
            this.thumbnails.value = thumbnails.map {
                if (it.id == item.id) it.copy(url = thumbnailUrl) else it
            }
        }
        return index
    }

    fun deleteThumbnail(thumbnailId: String) {
        thumbnails.value = thumbnails.value.filter {
            !(it.deletable && it.id == thumbnailId)
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

    fun addAndSelectThumbnail(thumbnailUrl: String) {
        val index = addThumbnail(thumbnailUrl)
        selectThumbnail(index)
    }

    fun updateThumbnailUrlAndSelect(thumbnailId: String, thumbnailUrl: String) {
        val index = updateThumbnailUrl(thumbnailId, thumbnailUrl)
        selectThumbnail(index)
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Tags
    //////////////////////////////////////////////////////////////////////////////////////////

    fun addTag(name: String, index: Int = -1): Int {
        val tags = this.tags.value.toMutableList()
        tags.add(
            if (index == -1) tags.size - 1 else index,
            TagDto(name, 1)
        )
        this.tags.value = tags
        return tags.indexOfFirst { it.name == name }
    }

    fun updateTagName(previousName: String, name: String): Int {
        val tags = this.tags.value
        val tag = tags.firstOrNull { it.name == previousName }
        val index = tag?.let { tags.indexOf(it) } ?: -1
        tag?.let { item ->
            this.tags.value = tags.map {
                if (it.name == item.name) it.copy(name = name) else it
            }
        }
        return index
    }

    fun deleteTag(name: String) {
        tags.value = tags.value.filter {
            it.name != name
        }
    }

}