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
    val thumbnailUrl: String?
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
    val item: ItemData,
    val fieldsContent: List<FieldContentDto>
)