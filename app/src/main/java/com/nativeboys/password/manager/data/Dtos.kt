package com.nativeboys.password.manager.data

import android.os.Parcelable
import com.zeustech.zeuskit.ui.rv.ListAdapterItem
import kotlinx.parcelize.Parcelize
import java.util.*

data class CategoryDto(
    val id: String,
    val description: String,
    val selected: Boolean
) : ListAdapterItem<CategoryDto> {

    override fun areItemsTheSame(model: CategoryDto) = id == model.id

    override fun areContentsTheSame(model: CategoryDto) = this == model
}

data class ItemDto(
    val itemId: String,
    val itemName: String,
    val itemDescription: String,
    val itemCategoryId: String,
    val thumbnailUrl: String?,
    val lastModificationDate: Date,
    val favoriteItem: Boolean
) : ListAdapterItem<ItemDto> {

    override fun areItemsTheSame(model: ItemDto) = itemId == model.itemId

    override fun areContentsTheSame(model: ItemDto) = this == model
}

@Parcelize
data class FieldContentDto(
    val contentId: String = UUID.randomUUID().toString(),
    val textContent: String,
    val fieldId: String,
    val fieldName: String,
    val fieldType: String
) : Parcelable, ListAdapterItem<FieldContentDto> {

    override fun areItemsTheSame(model: FieldContentDto) = contentId == model.contentId

    override fun areContentsTheSame(model: FieldContentDto) = this == model

}

@Parcelize
data class ItemFieldsContentDto(
    val id: String,
    val name: String,
    val description: String,
    val notes: String?,
    val tags: String?,
    val favorite: Boolean,
    val requiresPassword: Boolean,
    val categoryId: String,
    val thumbnailUrl: String?,
    val fieldsContent: List<FieldContentDto>
) : Parcelable {

    constructor(
        itemData: ItemData,
        fieldsContent: List<FieldContentDto>,
        thumbnailUrl: String?
    ) : this(itemData.id, itemData.name, itemData.description, itemData.notes, itemData.tags, itemData.favorite, itemData.requiresPassword, itemData.categoryId, thumbnailUrl, fieldsContent)

    fun tagsAsDto(addDefault: Boolean = true): List<TagDto> {
        val tagList = tags?.split(",") ?: emptyList()
        val tagsDto = tagList.map { TagDto(it, 1) }.toMutableList()
        if (addDefault) tagsDto.add(TagDto())
        return tagsDto
    }

}

@Parcelize
data class ThumbnailDto(
    val id: String = "",
    val url: String = "",
    val deletable: Boolean = false,
    val type: Int = 3 // 1: Regular, 2: Selected, 3: Button
) : Parcelable, ListAdapterItem<ThumbnailDto> {
    constructor(thumbnail: ThumbnailData, deletable: Boolean, type: Int) : this(thumbnail.id, thumbnail.url, deletable, type)

    override fun areItemsTheSame(model: ThumbnailDto) = model.id == id

    override fun areContentsTheSame(model: ThumbnailDto) = model == this

}

@Parcelize
data class TagDto(
    val name: String = "",
    val type: Int = 3
) : Parcelable, ListAdapterItem<TagDto> {

    override fun areItemsTheSame(model: TagDto) = name == model.name

    override fun areContentsTheSame(model: TagDto) = this == model

}