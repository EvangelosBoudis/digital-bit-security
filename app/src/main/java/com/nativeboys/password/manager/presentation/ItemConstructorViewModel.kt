package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.*
import com.nativeboys.password.manager.data.repositories.fields.FieldRepository
import com.nativeboys.password.manager.data.repositories.items.ItemRepository
import com.nativeboys.password.manager.data.repositories.thumbnails.ThumbnailRepository
import com.nativeboys.password.manager.util.safeSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
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

    private val passwordFromCacheOrDatabase: Boolean
        get() {
            return state.get<Boolean>(PASSWORD_REQUIRED) ?: item?.requiresPassword ?: true
        }

    private val notesFromCacheOrDatabase: String?
        get() {
            return state.get<String>(NOTES) ?: item?.notes
        }

    val observeTags = state.getLiveData<List<TagDto>>(TAGS)
    val observeThumbnails = state.getLiveData<List<ThumbnailDto>>(THUMBNAILS)

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            initItem()
            val noTags = state.get<List<TagDto>>(TAGS)?.isEmpty() ?: true
            if (noTags) { // init cache data
                val tags = (item?.tagsAsDto ?: emptyList()).toMutableList()
                tags.add(TagDto())
                state.safeSet(TAGS, tags, this)
            }
            val noThumbnails = state.get<List<ThumbnailDto>>(THUMBNAILS)?.isEmpty() ?: true
            if (noThumbnails) { // init cache data
                state.safeSet(THUMBNAILS, itemRepository.findAllThumbnailsDto(itemId ?: ""), this)
            }
        }
    }

    private suspend fun initItem() {
        if (item == null && itemId != null) {
            item = itemRepository.findItemFieldsContentById(itemId)
        }
    }

    fun getInitFieldsContent() = liveData {
        initItem()
        emit(getFieldsContentMergedByCacheAndDatabase())
    }

    fun getInitPasswordIsRequired() = liveData {
        initItem()
        emit(passwordFromCacheOrDatabase)
    }

    fun getInitNotes() = liveData {
        initItem()
        emit(notesFromCacheOrDatabase)
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

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Thumbnails
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun getThumbnails() = state[THUMBNAILS] ?: emptyList<ThumbnailDto>()

    private fun addThumbnail(thumbnailUrl: String): Int {
        val thumbnails = getThumbnails().toMutableList()
        thumbnails.add(
            thumbnails.size - 1,
            ThumbnailDto(UUID.randomUUID().toString(), thumbnailUrl, true, 1)
        )
        state.safeSet(THUMBNAILS, thumbnails, viewModelScope)
        return thumbnails.indexOfFirst { it.url == thumbnailUrl }
    }

    private fun updateThumbnailUrl(thumbnail: ThumbnailDto, thumbnailUrl: String): Int {
        val thumbnails = getThumbnails()
        val thumbnailIndex = thumbnails.indexOf(thumbnail)
        val modifiedThumbnails = thumbnails.map {
            if (it.id == thumbnail.id) it.copy(url = thumbnailUrl) else it
        }
        state.safeSet(THUMBNAILS, modifiedThumbnails, viewModelScope)
        return thumbnailIndex
    }

    fun deleteThumbnail(thumbnail: ThumbnailDto) = liveData {
        val count = itemRepository.countAllItemsWithThumbnailId(thumbnail.id)
        val item = itemId?.let { itemRepository.findItemById(it) }
        val deletable = count == 0 || count == 1 && (item?.thumbnailId == thumbnail.id)
        if (deletable) {
            val thumbnails = state[THUMBNAILS] ?: emptyList<ThumbnailDto>()
            val modifiedThumbnails = thumbnails.filter {
                !(it.deletable && it.id == thumbnail.id)
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
        val index = addThumbnail(thumbnailUrl.trim { it <= ' ' })
        selectThumbnail(index)
    }

    fun updateThumbnailUrlAndSelect(thumbnail: ThumbnailDto, thumbnailUrl: String) {
        val index = updateThumbnailUrl(thumbnail, thumbnailUrl.trim { it <= ' ' })
        selectThumbnail(index)
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Tags
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun getTags() = state[TAGS] ?: emptyList<TagDto>()

    fun addTag(name: String) {
        val tags = getTags().toMutableList()
        tags.add(tags.size - 1, TagDto(name.trim { it <= ' ' }, 1))
        state.safeSet(TAGS, tags, viewModelScope)
    }

    fun updateTagName(tag: TagDto, name: String) {
        val trimmedName = name.trim { it <= ' ' }
        val tags = getTags()
        val modifiedTags = tags.map {
            if (it.name == tag.name) it.copy(name = trimmedName) else it
        }
        state.safeSet(TAGS, modifiedTags, viewModelScope)
    }

    fun deleteTag(tag: TagDto) {
        state.safeSet(
            TAGS,
            getTags().filter { it.name != tag.name },
            viewModelScope
        )
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Other
    //////////////////////////////////////////////////////////////////////////////////////////

    fun submitItem() = liveData {

        val thumbnails = getThumbnails()
            .filter { it.url.isNotBlank() }

        val fieldsContent = getFieldsContentMergedByCacheAndDatabase()

        val tags = getTags()
            .filter { it.name.isNotBlank() }
            .joinToString(",") { it.name }

        val contents = fieldsContent
            .filter { it.fieldId != FIELD_NAME_ID && it.fieldId != FIELD_DESCRIPTION_ID }

        try {
            val categoryId = this@ItemConstructorViewModel.categoryId ?: item?.categoryId ?: ""
            if (categoryId.isEmpty()) throw SaveItemException("Category does not provided")

            val thumbnailId = thumbnails.firstOrNull { it.type == 2 }?.id ?: ""
            if (thumbnailId.isEmpty()) throw SaveItemException("Thumbnail does not provided")

            val name = fieldsContent.firstOrNull { it.fieldId == FIELD_NAME_ID }?.textContent ?: ""
            if (name.isEmpty()) throw SaveItemException("Name does not provided")

            val description = fieldsContent.firstOrNull { it.fieldId == FIELD_DESCRIPTION_ID }?.textContent ?: ""
            if (description.isEmpty()) throw SaveItemException("Description does not provided")

            thumbnailRepository.replaceAllThumbnails(thumbnails)
            itemRepository.saveOrUpdateItem(
                item?.id, name, description,
                notesFromCacheOrDatabase,
                tags, item?.favorite ?: false,
                passwordFromCacheOrDatabase,
                categoryId, thumbnailId, contents
            )
            emit(Result.Success(true))

        } catch (e: Exception) {
            emit(Result.Error(e))
        }

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