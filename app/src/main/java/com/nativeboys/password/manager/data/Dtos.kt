package com.nativeboys.password.manager.data

import com.zeustech.zeuskit.ui.rv.ListAdapterItem
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

data class FieldContentDto(
    val contentId: String = UUID.randomUUID().toString(),
    val textContent: String,
    val fieldName: String,
    val fieldType: String
)

data class ItemFieldsContentDto(
    val id: String,
    val name: String,
    val description: String,
    val notes: String?,
    val tags: String?,
    val favorite: Boolean,
    val thumbnailUrl: String?,
    val fieldsContent: List<FieldContentDto>
) {

    constructor(
        itemData: ItemData,
        fieldsContent: List<FieldContentDto>,
        thumbnailUrl: String?
    ) : this(itemData.id, itemData.name, itemData.description, itemData.notes, itemData.tags, itemData.favorite, thumbnailUrl, fieldsContent)

    val tagsAsList: List<String>
        get() = tags?.split(",") ?: emptyList()

}

data class ThumbnailDto(
    val id: String = "",
    val url: String = "",
    val type: Int = 3 // 1: Regular, 2: Selected, 3: Button
) {
    constructor(thumbnail: ThumbnailData, type: Int) : this(thumbnail.id, thumbnail.url, type)
}

data class TagDto(
    val name: String = "",
    val type: Int = 3
)