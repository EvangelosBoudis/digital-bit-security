package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.CategoryWithFields
import com.nativeboys.password.manager.data.FieldData
import com.nativeboys.password.manager.data.UICategoryIcon
import com.nativeboys.password.manager.data.UIField
import com.nativeboys.password.manager.data.repository.CategoryRepository
import com.nativeboys.password.manager.util.allTypes
import com.nativeboys.password.manager.util.safeSet
import com.nativeboys.password.manager.util.toComparable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import java.util.*

class CategoryConstructorViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val categoryId: String? = state.get<String>(CATEGORY_ID)
    private var categoryWithFields: CategoryWithFields? = null

    val categoriesTypes = allTypes()

    val fields = state.getLiveData<List<FieldData>>(FIELDS).map { fields ->
        fields.map { field ->
            UIField(
                field.id,
                field.name,
                categoriesTypes.firstOrNull { it.code == field.type }?.description,
                if (field.id.isNotEmpty()) 1 else 2
            )
        }
    }

    val thumbnailCode = state.getLiveData<String>(THUMBNAIL_CODE)

    val thumbnailSearchKey: String?
        get() {
            return state.get<String>(THUMBNAIL_SEARCH_KEY)
        }

    private val materialIcons: List<MaterialDrawableBuilder.IconValue>
        get() {
            return EnumSet.allOf(MaterialDrawableBuilder.IconValue::class.java).toList()
        }

    val categoriesIcons = combine(
        state.getLiveData<String>(THUMBNAIL_SEARCH_KEY).asFlow(),
        state.getLiveData<String>(THUMBNAIL_CODE).asFlow()
    ) { searchKey, thumbnailCode ->
        val comparableSearchKey = searchKey?.toComparable() ?: ""
        materialIcons
            .filter { it.name.toComparable().contains(comparableSearchKey) }
            .map { UICategoryIcon(it.name, it.name == thumbnailCode) }
    }.asLiveData()


    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            initCategory()

            val emptyThumbnailSearchKey = state.get<String>(THUMBNAIL_SEARCH_KEY)?.isEmpty() ?: true
            if (emptyThumbnailSearchKey) state.safeSet(THUMBNAIL_SEARCH_KEY, "", this)

            val emptyThumbnailCode = state.get<String>(THUMBNAIL_CODE)?.isEmpty() ?: true
            if (emptyThumbnailCode) {
                categoryWithFields?.category?.thumbnailCode?.let {
                    state.safeSet(THUMBNAIL_CODE, it, this)
                }
            }

            val emptyFields = state.get<List<FieldData>>(FIELDS)?.isEmpty() ?: true
            if (emptyFields) { // init cache data
                val fields = (categoryWithFields?.fields ?: emptyList()).toMutableList()
                fields.add(FieldData("", "", "", ""))
                state.safeSet(FIELDS, fields, this)
            }

        }
    }

    private suspend fun initCategory() {
        if (categoryWithFields == null && categoryId != null) {
            categoryWithFields = categoryRepository.findCategoryWithFieldsById(categoryId)
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Name
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun getNameFromCacheOrDatabase(): String? {
        return state.get<String>(NAME) ?: categoryWithFields?.category?.name
    }

    fun getInitName() = liveData {
        initCategory()
        emit(getNameFromCacheOrDatabase())
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// ThumbnailCode
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun getThumbnailCodeFromCacheOrDatabase(): String? {
        return state.get<String>(THUMBNAIL_CODE) ?: categoryWithFields?.category?.thumbnailCode
    }

    fun <T> updateUserCache(fieldId: String, value: T) {
        state[fieldId] = value
    }

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
        const val NAME = "NAME"
        const val THUMBNAIL_CODE = "THUMBNAIL_CODE"
        const val FIELDS = "FIELDS"

        const val THUMBNAIL_SEARCH_KEY = "THUMBNAIL_SEARCH_KEY"
    }

}