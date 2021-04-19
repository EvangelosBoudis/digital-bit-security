package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.*
import com.nativeboys.password.manager.data.repository.FieldRepository
import com.nativeboys.password.manager.data.repository.ItemRepository
import com.nativeboys.password.manager.data.repository.ThumbnailRepository
import com.nativeboys.password.manager.other.safeSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ItemConstructorViewModel @ViewModelInject constructor(
    private val itemRepository: ItemRepository,
    private val fieldRepository: FieldRepository,
    private val thumbnailRepository: ThumbnailRepository,
    @Assisted private val state: SavedStateHandle
): ViewModel() {

    private val itemId: String? = state[ITEM_ID]
    private val categoryId: String? = state[CATEGORY_ID]
    private var item: ItemFieldsContentDto? = null

    val tags = state.getLiveData<List<TagDto>>(TAGS)
    val thumbnails = state.getLiveData<List<ThumbnailDto>>(THUMBNAILS)

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            initItem()

            val emptyTags = state.get<List<TagDto>>(TAGS)?.isEmpty() ?: true
            if (emptyTags) state.safeSet(TAGS, item?.tagsAsDto() ?: emptyList(), this) // init cache data

            val emptyThumbnails = state.get<List<ThumbnailDto>>(THUMBNAILS)?.isEmpty() ?: true
            if (emptyThumbnails) state.safeSet(THUMBNAILS, itemRepository.findAllThumbnailsDto(itemId ?: ""), this) // init cache data
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Item
    //////////////////////////////////////////////////////////////////////////////////////////

    private suspend fun initItem() {
        if (item == null && itemId != null) {
            item = itemRepository.findItemFieldsContentById(itemId)
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Fields
    //////////////////////////////////////////////////////////////////////////////////////////

    /** Merge database fields with cache */
    private suspend fun getFieldsContentMergedByCacheAndDatabase(): List<FieldContentDto> {

        val nameContent = state[FIELD_NAME_ID] ?: item?.name ?: ""
        val descriptionContent = state[FIELD_DESCRIPTION_ID] ?: item?.description ?: ""
        val allFieldContent = listOf(
            FieldContentDto("", nameContent, FIELD_NAME_ID, "Name", "text"),
            FieldContentDto("", descriptionContent, FIELD_DESCRIPTION_ID, "Description", "text")
        ).toMutableList()

        item?.fieldsContent?.let { preFieldsContent ->
            val fieldsContent = preFieldsContent
                .map { it.copy(textContent = state[it.fieldId] ?: it.textContent) }
            allFieldContent.addAll(fieldsContent)
        }

        categoryId?.let { categoryId ->
            val fieldsContent = fieldRepository
                .findAllFieldsByCategoryId(categoryId)
                .map { FieldContentDto(state[it.id] ?: "", it) }
            allFieldContent.addAll(fieldsContent)
        }

        return allFieldContent
    }

    fun getInitFieldsContent() = liveData {
        initItem()
        emit(getFieldsContentMergedByCacheAndDatabase())
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Password
    //////////////////////////////////////////////////////////////////////////////////////////

    /** Get from cache or database */
    private fun getPasswordFromCacheOrDatabase(): Boolean {
        return state.get<Boolean>(PASSWORD_REQUIRED) ?: item?.requiresPassword ?: true
    }

    fun getInitPasswordIsRequired() = liveData {
        initItem()
        emit(getPasswordFromCacheOrDatabase())
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Notes
    //////////////////////////////////////////////////////////////////////////////////////////

    /** Get from cache or database */
    private fun getNotesFromCacheOrDatabase(): String? {
        return state.get<String>(NOTES) ?: item?.notes
    }

    fun getInitNotes() = liveData {
        initItem()
        emit(getNotesFromCacheOrDatabase())
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Thumbnails
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun getThumbnails() = state[THUMBNAILS] ?: emptyList<ThumbnailDto>()

    private fun addThumbnail(thumbnailUrl: String, index: Int = -1): Int {
        val thumbnails = getThumbnails().toMutableList()
        thumbnails.add(
            if (index == -1) thumbnails.size - 1 else index,
            ThumbnailDto(UUID.randomUUID().toString(), thumbnailUrl, true, 1)
        )
        state.safeSet(THUMBNAILS, thumbnails, viewModelScope)
        return thumbnails.indexOfFirst { it.url == thumbnailUrl }
    }

    private fun updateThumbnailUrl(thumbnailId: String, thumbnailUrl: String): Int {
        val thumbnails = getThumbnails()
        val thumbnail = thumbnails.firstOrNull { it.id == thumbnailId }
        val index = thumbnail?.let { thumbnails.indexOf(it) } ?: -1
        thumbnail?.let { item ->
            val modifiedThumbnails = thumbnails.map {
                if (it.id == item.id) it.copy(url = thumbnailUrl) else it
            }
            state.safeSet(THUMBNAILS, modifiedThumbnails, viewModelScope)
        }
        return index
    }

    fun deleteThumbnail(thumbnailId: String) = liveData {
        val count = itemRepository.getItemsCountWithThumbnailId(thumbnailId)
        val item = itemId?.let { itemRepository.findItemById(it) }
        val deletable = count == 0 || count == 1 && (item?.thumbnailId == thumbnailId)
        if (deletable) {
            val thumbnails = state[THUMBNAILS] ?: emptyList<ThumbnailDto>()
            val modifiedThumbnails = thumbnails.filter {
                !(it.deletable && it.id == thumbnailId)
            }
            state.safeSet(THUMBNAILS, modifiedThumbnails, viewModelScope)
        }
        emit(deletable)
    }

    fun selectThumbnail(thumbnail: ThumbnailDto) {
        val thumbnails = getThumbnails().map {
            if (it.type == 3) it else it.copy(type = if (it.id == thumbnail.id) 2 else 1)
        }
        state.safeSet(THUMBNAILS, thumbnails, viewModelScope)
    }

    private fun selectThumbnail(index: Int) {
        val thumbnails = getThumbnails().mapIndexed { thumbnailIndex, thumbnail ->
            if (thumbnail.type == 3) thumbnail else thumbnail.copy(type = if (index == thumbnailIndex) 2 else 1)
        }
        state.safeSet(THUMBNAILS, thumbnails, viewModelScope)
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

    private fun getTags() = state[TAGS] ?: emptyList<TagDto>()

    fun addTag(name: String, index: Int = -1): Int {
        val tags = getTags().toMutableList()
        tags.add(
            if (index == -1) tags.size - 1 else index,
            TagDto(name, 1)
        )
        state.safeSet(TAGS, tags, viewModelScope)
        return tags.indexOfFirst { it.name == name }
    }

    fun updateTagName(previousName: String, name: String): Int {
        val tags = getTags()
        val tag = tags.firstOrNull { it.name == previousName }
        val index = tag?.let { tags.indexOf(it) } ?: -1
        tag?.let { item ->
            val modifiedTags = tags.map {
                if (it.name == item.name) it.copy(name = name) else it
            }
            state.safeSet(TAGS, modifiedTags, viewModelScope)
        }
        return index
    }

    fun deleteTag(name: String) {
        state.safeSet(
            TAGS,
            getTags().filter { it.name != name },
            viewModelScope
        )
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Other
    //////////////////////////////////////////////////////////////////////////////////////////

    fun submitItem() = liveData {

        val thumbnailsDto = getThumbnails()

        val thumbnailId = thumbnailsDto
            .firstOrNull { it.type == 2 }?.id ?: ""

        thumbnailRepository.replaceAllThumbnails(thumbnailsDto)

        itemRepository.updateItem(
            item!!.id, item!!.categoryId, thumbnailId,
            getNotesFromCacheOrDatabase(),
            item?.favorite ?: false,
            getPasswordFromCacheOrDatabase(), getTags(),
            getFieldsContentMergedByCacheAndDatabase()
        )

        emit(true)
    }

    fun <T> updateUserCache(fieldId: String, value: T) {
        state[fieldId] = value
    }

    companion object {
        const val ITEM_ID = "ITEM_ID"
        const val CATEGORY_ID = "CATEGORY_ID"

        const val FIELD_NAME_ID = "NAME_ID"
        const val FIELD_DESCRIPTION_ID = "DESCRIPTION_ID"

        const val PASSWORD_REQUIRED = "PASSWORD_REQUIRED"
        const val NOTES = "NOTES"
        const val TAGS = "TAGS"
        const val THUMBNAILS = "THUMBNAILS"
    }

}