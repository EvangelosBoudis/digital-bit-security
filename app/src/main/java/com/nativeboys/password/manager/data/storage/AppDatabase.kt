package com.nativeboys.password.manager.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nativeboys.password.manager.data.*

@Database(entities = [ThumbnailData::class, CategoryData::class, FieldData::class, ItemData::class, ContentData::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun fieldDao(): FieldDao

    abstract fun thumbnailDao(): ThumbnailDao

    abstract fun itemDao(): ItemDao

    abstract fun contentDao(): ContentDao

}