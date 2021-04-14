package com.nativeboys.password.manager.ui.itemConstructor

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativeboys.password.manager.data.FieldContentDto
import com.nativeboys.password.manager.data.ItemFieldsContentDto
import com.nativeboys.password.manager.data.TagDto
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.data.repository.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

const val NAME_ID = "NAME"
const val DESCRIPTION_ID = "DESCRIPTION"
const val NOTES_ID = "NOTES"
const val PASSWORD_REQUIRED_ID = "PASSWORD_REQUIRED"

class ItemConstructorViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    val itemId: String? = state.get<String>("item_id")
    private val userCache: HashMap<String, String> = hashMapOf()
    private val itemDto: MutableStateFlow<ItemFieldsContentDto?> = MutableStateFlow(null)

    val passwordIsRequired = itemDto.map { item ->
        val required = item?.requiresPassword ?: true
        val value = userCache[PASSWORD_REQUIRED_ID]
        return@map if (value != null) value == "TRUE" else required
    }
    val notes = itemDto.map { item ->
        userCache[NOTES_ID] ?: item?.notes ?: ""
    }
    val transformedFieldContent = itemDto.map { item ->
        val nameContent = userCache[NAME_ID] ?: item?.name ?: ""
        val descriptionContent = userCache[DESCRIPTION_ID] ?: item?.description ?: ""
        val allFieldContent = listOf(
            FieldContentDto(NAME_ID, nameContent, "Name", "text"),
            FieldContentDto(DESCRIPTION_ID, descriptionContent, "Description", "text")
        ).toMutableList()
        item?.fieldsContent?.let { fieldsContent ->
            allFieldContent.addAll(fieldsContent.map {
                it.copy(textContent = userCache[it.contentId] ?: it.textContent)
            })
        }
        return@map allFieldContent
    }

    val thumbnails: MutableStateFlow<List<ThumbnailDto>> = MutableStateFlow(emptyList())
    val tags: MutableStateFlow<List<TagDto>> = MutableStateFlow(emptyList())

    init {
        itemId?.let { id ->
            viewModelScope.launch {
                val item = itemRepository.findItemFieldsContentById(id)
                this@ItemConstructorViewModel.itemDto.value = item
                tags.value = item.tagsAsDto()
                thumbnails.value = itemRepository.findAllThumbnailsDto(id)
            }
        }
    }

    fun updateUserCache(contentId: String, textContent: String) {
        userCache[contentId] = textContent
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