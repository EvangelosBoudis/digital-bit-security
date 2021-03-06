package com.nativeboys.password.manager.data

import android.os.Parcelable
import androidx.room.*
import com.nativeboys.uikit.rv.ListAdapterItem
import kotlinx.parcelize.Parcelize
import java.util.*

/*@Entity(tableName = "users")
@Parcelize
data class UserData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val email: String,
    @ColumnInfo(name = "master_password") val masterPassword: String,
    val avatarUrl: String
) : Parcelable*/

@Entity(tableName = "thumbnails")
@Parcelize
data class ThumbnailData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val url: String
) : Parcelable

@Entity(tableName = "categories")
@Parcelize
data class CategoryData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    @ColumnInfo(name = "thumbnail_code") val thumbnailCode: String,
    @ColumnInfo(name = "default_category") val defaultCategory: Boolean,
    // @ColumnInfo(name = "owner_id") val ownerId: String, // FK (Users) e.g ADMIN
    @ColumnInfo(name = "date_modified") val dateModified: Date = Date()
) : Parcelable, ListAdapterItem<CategoryData> {

    override fun areItemsTheSame(model: CategoryData) = id == model.id

    override fun areContentsTheSame(model: CategoryData) = this == model
}

@Entity(
    tableName = "fields",
    foreignKeys = [
        ForeignKey(
            entity = CategoryData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class FieldData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val type: String = "text",
    @ColumnInfo(name = "category_id") val categoryId: String // FK (Categories)
) : Parcelable

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = CategoryData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("category_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class ItemData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val notes: String?,
    val tags: String?,
    val favorite: Boolean,
    @ColumnInfo(name = "thumbnail_id") val thumbnailId: String, // FK (Thumbnails)
    @ColumnInfo(name = "date_modified") val dateModified: Date = Date(),
    @ColumnInfo(name = "requires_password") val requiresPassword: Boolean,
    @ColumnInfo(name = "category_id") val categoryId: String, // FK (Categories),
    // @ColumnInfo(name = "owner_id") val ownerId: String, // FK (Users),
) : Parcelable

@Entity(
    tableName = "contents",
    foreignKeys = [
        ForeignKey(
            entity = ItemData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("item_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FieldData::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("field_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
@Parcelize
data class ContentData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "field_id") val fieldId: String, // FK (Fields),
    @ColumnInfo(name = "item_id") val itemId: String, // FK (Items),
    val content: String
) : Parcelable

//////////////////////////////////////////////////////////////////////////////////////////
/// Relationships Between Objects
//////////////////////////////////////////////////////////////////////////////////////////

data class CategoryWithFields(
    @Embedded val category: CategoryData,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val fields: List<FieldData>
)

data class ItemWithContents(
    @Embedded val item: ItemData,
    @Relation(
        parentColumn = "id",
        entityColumn = "item_id"
    )
    val contents: List<ContentData>
)

