package com.nativeboys.couchbase.lite.android.dao.data

import java.util.*

data class TagModel(
    val id: String? = UUID.randomUUID().toString(),
    val description: String
)