package com.nativeboys.password.manager.data

import java.sql.Timestamp
import java.util.*

data class ThumbnailData(
    val url: String // PK
)

data class FieldData(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val type: Int = -1,
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
    val description: String,
    val notes: String?,
    val tags: String?,
    val thumbnailUrl: String,
    val dateModified: Timestamp,
    val requiresPassword: Boolean,
    val favorite: Boolean,
    val categoryId: String, // FK (Categories),
    val ownerId: String, // FK (Users),
) {

    val tagsAsList: List<String>
        get() = tags?.split(",") ?: emptyList()

}

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

data class FilterModel(
    val id: String,
    val description: String,
    val selected: Boolean
)

data class TagModel(
    val name: String,
    val type: Int
)

data class FieldContentModel(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val type: Int,
    val hidden: Boolean,
    val content: String
)

data class ThumbnailModel(
    val url: String = "",
    val type: Int // 1: Regular, 2: Selected, 3: Button
)
