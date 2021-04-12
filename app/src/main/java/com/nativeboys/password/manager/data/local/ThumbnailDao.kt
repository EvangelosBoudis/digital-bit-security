package com.nativeboys.password.manager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nativeboys.password.manager.data.ThumbnailData

@Dao
interface ThumbnailDao {

    @Query("SELECT * FROM thumbnails WHERE id = :id")
    suspend fun findById(id: String): ThumbnailData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(thumbnails: List<ThumbnailData>)

}