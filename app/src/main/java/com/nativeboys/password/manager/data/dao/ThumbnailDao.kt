package com.nativeboys.password.manager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nativeboys.password.manager.data.ThumbnailData

@Dao
interface ThumbnailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(thumbnails: List<ThumbnailData>)

}