package com.nativeboys.password.manager.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp
import java.util.*

@Entity(tableName = "users")
@Parcelize
data class UserEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val email: String,
    @ColumnInfo(name = "master_password") val masterPassword: String,
    val avatarUrl: String
) : Parcelable

@Entity(tableName = "thumbnails")
@Parcelize
data class ThumbnailEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val url: String
) : Parcelable

@Entity(tableName = "categories")
@Parcelize
data class CategoryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    @ColumnInfo(name = "thumbnail_code") val thumbnailCode: String,
    @ColumnInfo(name = "owner_id") val ownerId: String, // FK (Users) e.g ADMIN
) : Parcelable {

    val adminCategory: Boolean
        get() = ownerId == "ADMIN"

}

@Entity(tableName = "fields")
@Parcelize
data class FieldEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: Int = -1, // TODO: Fix
    @ColumnInfo(name = "category_id") val categoryId: String // FK (Categories)
) : Parcelable

@Entity(tableName = "items")
@Parcelize
data class ItemEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val notes: String?,
    val tags: String?,
    val favorite: Boolean,
    @ColumnInfo(name = "thumbnail_id") val thumbnailId: String, // FK (Thumbnails)
    @ColumnInfo(name = "date_modified") val dateModified: String = Timestamp(System.currentTimeMillis()).toString(),
    @ColumnInfo(name = "requires_password") val requiresPassword: Boolean,
    @ColumnInfo(name = "category_id") val categoryId: String, // FK (Categories),
    @ColumnInfo(name = "owner_id") val ownerId: String, // FK (Users),
) : Parcelable {

    val tagsAsList: List<String>
        get() = tags?.split(",") ?: emptyList()

}

@Entity(tableName = "items_fields")
@Parcelize
data class ItemFieldEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "field_id") val fieldId: String, // FK (Fields),
    @ColumnInfo(name = "item_id") val itemId: String, // FK (Items),
    val content: String
) : Parcelable

