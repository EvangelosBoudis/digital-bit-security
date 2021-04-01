package com.nativeboys.password.manager.other

import java.util.*

data class TagModel(
    val name: String,
    val type: Int
)

data class FieldContentModel(
    val id: String = UUID.randomUUID().toString(), // PK
    val name: String,
    val type: String,
    val content: String
)

data class ThumbnailModel(
    val url: String = "",
    val type: Int // 1: Regular, 2: Selected, 3: Button
)
