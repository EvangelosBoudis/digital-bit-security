package com.nativeboys.password.manager.data

import java.sql.Timestamp
import java.util.*

data class FieldData(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val type: Int,
    val hidden: Boolean,
    val categoryId: String // FK (Categories)
)

data class CategoryData(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val thumbnailUrl: String,
    val ownerId: String, // FK (Users)
    val defaultCategory: Boolean
)

data class ItemData(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val notes: String,
    val tags: List<String>,
    val thumbnailUrl: String?,
    val dateModified: Timestamp,
    val requiresPassword: Boolean,
    val favorite: Boolean,
    val categoryId: String, // FK (Categories),
    val ownerId: String, // FK (Users),
)

data class ItemFieldData(
    val id: String = UUID.randomUUID().toString(),
    val fieldId: String,
    val itemId: String,
    val content: String
)

data class UserData(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val masterPassword: String,
    val avatarUrl: String
)

////////////////////////////////////////////////////////////////

data class ContentFieldModel(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val type: Int,
    val hidden: Boolean,
    val content: String
)

////////////////////////////////////////////////////////////////

data class ItemModel(
    val id: String = UUID.randomUUID().toString(),
    val webSite: String,
    val email: String,
    val password: String,
    val notes: String,
    val tagIds: String,
    val thumbnailUrl: String?
)

data class TagModel(
    val id: String = UUID.randomUUID().toString(),
    val description: String = ""
)

data class AdapterTagModel(
    val id: String = UUID.randomUUID().toString(),
    val description: String = "",
    val type: Int // 1: Regular, 2: Selected, 3: Button
) {

    constructor(tag: TagModel, type: Int): this(tag.id, tag.description, type)

    fun asTagModel() = TagModel(id, description)

}

data class AdapterThumbnailModel(
    val url: String = "",
    val type: Int // 1: Regular, 2: Selected, 3: Button
)