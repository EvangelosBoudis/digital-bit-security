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
    val itemTags: String?,
    val favoriteItem: Boolean,
    val itemCategoryId: String,
    val lastModificationDate: Date,
    val thumbnailUrl: String?
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

    constructor(textContent: String, field: FieldData) : this(textContent = textContent, fieldId = field.id, fieldName = field.name, fieldType = field.type)

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

    val formattedTags: String?
        get() {
            return this.tags?.split(",")?.joinToString(", ")
        }

    val tagsAsDto: List<TagDto>
        get() {
            return (this.tags ?: "")
                .split(",")
                .map { TagDto(it, 1) }
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
