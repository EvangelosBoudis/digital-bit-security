package com.nativeboys.couchbase.lite.android.dao.data

import java.util.*

data class PasswordModel(
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