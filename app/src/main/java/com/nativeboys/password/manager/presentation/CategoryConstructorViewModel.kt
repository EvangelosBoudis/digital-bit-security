package com.nativeboys.password.manager.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nativeboys.password.manager.data.*
import com.nativeboys.password.manager.data.repositories.category.CategoryRepository
import com.nativeboys.password.manager.util.allTypes
import com.nativeboys.password.manager.util.safeSet
import com.nativeboys.password.manager.util.toComparable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import java.lang.Exception
import java.util.*

class CategoryConstructorViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val categoryId: String? = state.get<String>(CATEGORY_ID)
    private val generatedCategoryId = UUID.randomUUID().toString()

    private var categoryWithFields: CategoryWithFields? = null

    val thumbnailSearchKey: String?
        get() {
            return state.get<String>(THUMBNAIL_SEARCH_KEY)
        }

    private val materialIcons: List<MaterialDrawableBuilder.IconValue>
        get() {
            return EnumSet.allOf(MaterialDrawableBuilder.IconValue::class.java).toList()
        }

    val observeCategoriesIcons = combine(
        state.getLiveData<String>(THUMBNAIL_SEARCH_KEY).asFlow(),
        state.getLiveData<String>(THUMBNAIL_CODE).asFlow()
    ) { searchKey, thumbnailCode ->
        val comparableSearchKey = searchKey?.toComparable() ?: ""
        materialIcons
            .filter { it.name.toComparable().contains(comparableSearchKey) }
            .map { UICategoryIcon(it.name, it.name == thumbnailCode) }
    }.asLiveData()

    private val thumbnailCodeFromCacheOrDatabase: String?
        get() {
            return state.get<String>(THUMBNAIL_CODE) ?: categoryWithFields?.category?.thumbnailCode
        }

    val observeThumbnailCode = state.getLiveData<String>(THUMBNAIL_CODE)

    private val nameFromCacheOrDatabase: String?
        get() {
            return state.get<String>(NAME) ?: categoryWithFields?.category?.name
        }

    val categoriesTypes = allTypes()

    val observeFields = state.getLiveData<List<FieldData>>(FIELDS)
        .asFlow().map { fields ->
            fields.map { field ->
                UIField(
                    field.id,
                    state[FIELD_NAME_.plus(field.id)] ?: field.name,
                    categoriesTypes.firstOrNull { it.code == field.type }?.description,
                    if (field.id.isNotEmpty()) 1 else 2
                )
            }
        }

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            initCategory()
            val noThumbnailSearchKey = state.get<String>(THUMBNAIL_SEARCH_KEY)?.isEmpty() ?: true
            if (noThumbnailSearchKey) {
                state.safeSet(THUMBNAIL_SEARCH_KEY, "", this)
            }
            val noThumbnailCode = state.get<String>(THUMBNAIL_CODE)?.isEmpty() ?: true
            if (noThumbnailCode) {
                val thumbnailCode = categoryWithFields?.category?.thumbnailCode ?: MaterialDrawableBuilder.IconValue.IMAGE.name
                state.safeSet(THUMBNAIL_CODE, thumbnailCode, this)
            }
            val noName = state.get<String>(NAME)?.isEmpty() ?: true
            if (noName) {
                categoryWithFields?.category?.name?.let {
                    state.safeSet(NAME, it, this)
                }
            }
            val noFields = state.get<List<FieldData>>(FIELDS)?.isEmpty() ?: true
            if (noFields) { // init cache data
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

    fun getInitName() = liveData {
        initCategory()
        emit(nameFromCacheOrDatabase)
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Fields
    //////////////////////////////////////////////////////////////////////////////////////////

    private fun getFields(): List<FieldData> {
        val fields = state[FIELDS] ?: emptyList<FieldData>()
        return fields.map { field ->
            field.copy(name = state[FIELD_NAME_.plus(field.id)] ?: field.name)
        }
    }

    fun addNewField() {
        val fields = getFields().toMutableList()
        fields.add(
            fields.size - 1,
            FieldData(name = "", categoryId = categoryId ?: generatedCategoryId)
        )
        state.safeSet(FIELDS, fields, viewModelScope)
    }

    fun updateFieldType(typeIndex: Int) {
        val typeCode = categoriesTypes[typeIndex].code
        val fieldId: String = state[PENDING_FIELD_ID_FOR_TYPE] ?: return
        state.safeSet(
            FIELDS,
            getFields().map { if (it.id == fieldId) it.copy(type = typeCode) else it },
            viewModelScope
        )
    }

    fun updateFieldName(fieldId: String, name: String) {
        state[FIELD_NAME_.plus(fieldId)] = name
    }

    fun deleteField(fieldId: String) {
        state.safeSet(
            FIELDS,
            getFields().filter { it.id != fieldId },
            viewModelScope
        )
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    /// Other
    //////////////////////////////////////////////////////////////////////////////////////////

    fun submit() = liveData {

        try {

            val name = nameFromCacheOrDatabase ?: ""
            if (name.isEmpty()) throw SaveItemException("Name does not provided")

            val thumbnailCode = thumbnailCodeFromCacheOrDatabase ?: ""
            if (thumbnailCode.isEmpty()) throw SaveItemException("Thumbnail does not provided")

            val fields = getFields().filter { it.id.isNotEmpty() }
            if (fields.isEmpty()) throw SaveItemException("Categories require at least one field")

            val category = CategoryData(
                categoryId ?: generatedCategoryId,
                name, thumbnailCode,
                categoryWithFields?.category?.defaultCategory ?: false
            )

            emit(Result.Success(categoryRepository.editCategory(category, fields, categoryId == null)))

        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    fun <T> updateUserCache(fieldId: String, value: T) {
        state[fieldId] = value
    }

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"

        const val NAME = "NAME"
        const val THUMBNAIL_CODE = "THUMBNAIL_CODE"

        const val FIELDS = "FIELDS"
        const val FIELD_NAME_ = "FIELD_NAME_"

        const val THUMBNAIL_SEARCH_KEY = "THUMBNAIL_SEARCH_KEY"
        const val PENDING_FIELD_ID_FOR_TYPE = "PENDING_FIELD_ID_FOR_TYPE"
    }

}