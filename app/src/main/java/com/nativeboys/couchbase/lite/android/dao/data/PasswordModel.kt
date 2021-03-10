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