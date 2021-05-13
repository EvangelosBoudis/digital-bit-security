package com.nativeboys.password.manager.data

import android.os.Parcelable
import androidx.annotation.StringRes
import com.nativeboys.password.manager.R
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
    val thumbnailUrl: String?,
    val requestPassword: Boolean
) : ListAdapterItem<ItemDto> {

    override fun areItemsTheSame(model: ItemDto) = itemId == model.itemId

    override fun areContentsTheSame(model: ItemDto) = this == model
}

@Parcelize
data class FieldContentDto(
    val contentId: String? = UUID.randomUUID().toString(),
    val textContent: String?,
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
            val tags = this.tags ?: ""
            return if (tags.isNotEmpty()) tags.split(",").map { TagDto(it, 1) }
            else emptyList()
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

data class CountDto(
    val entityId: String,
    val rowsCount: Int
)

data class UICategoryIcon(
    val thumbnailCode: String,
    val selected: Boolean = false
) : ListAdapterItem<UICategoryIcon> {

    override fun areItemsTheSame(model: UICategoryIcon) = thumbnailCode == model.thumbnailCode

    override fun areContentsTheSame(model: UICategoryIcon) = this == model

}

data class UIField(
    val id: String,
    val name: String,
    val typeDescription: String?,
    val cellType: Int = 1 // 1: Field, 2: Add Btn
) : ListAdapterItem<UIField> {

    override fun areItemsTheSame(model: UIField) = id == model.id

    override fun areContentsTheSame(model: UIField) = this == model

}

data class UIMasterPasswordRequirement(
    @StringRes val descriptionResId: Int,
    val accepted: Boolean
) : ListAdapterItem<UIMasterPasswordRequirement> {

    override fun areItemsTheSame(model: UIMasterPasswordRequirement) = descriptionResId == model.descriptionResId

    override fun areContentsTheSame(model: UIMasterPasswordRequirement) = this == model

}

data class MasterPasswordRequirement(
    val regularExpression: String,
    @StringRes val descriptionResId: Int,
) {

    companion object {

        private const val AT_LEAST_TREE_CAPITAL_LETTERS_IN_ANY_ORDER = "^(?=(.*[A-Z]){3,}).+$"
        private const val AT_LEAST_ONE_LOWERCASE_LETTER_IN_ANY_ORDER = "^(?=(.*[a-z]){1,}).+$"
        private const val AT_LEAST_TWO_DIGITS_IN_ANY_ORDER = "^(?=(.*[0-9]){2,}).+$" // "^(?=.*?[0-9]{2,}).+$"
        private const val AT_LEAST_THREE_SPECIAL_CHARACTERS_IN_ANY_ORDER = "^(?=(.*[!#$%&()*+,-./:;<=>?@_{|}~^]){3,}).+$" // "^(?=.*?[#?!@$%^&*-]{3,}).+$"
        private const val AT_LEAST_NINE_CHARACTERS = "^.{9,}$"
        private const val NO_WHITESPACE = "^(?=\\S+$).+$"

        val available = listOf(
            MasterPasswordRequirement(AT_LEAST_TREE_CAPITAL_LETTERS_IN_ANY_ORDER, R.string.capital_letters),
            MasterPasswordRequirement(AT_LEAST_ONE_LOWERCASE_LETTER_IN_ANY_ORDER, R.string.lowercase_letters),
            MasterPasswordRequirement(AT_LEAST_TWO_DIGITS_IN_ANY_ORDER, R.string.digits),
            MasterPasswordRequirement(AT_LEAST_THREE_SPECIAL_CHARACTERS_IN_ANY_ORDER, R.string.special_characters),
            MasterPasswordRequirement(AT_LEAST_NINE_CHARACTERS, R.string.password_length),
            MasterPasswordRequirement(NO_WHITESPACE, R.string.no_whitespace),
        )

    }

}