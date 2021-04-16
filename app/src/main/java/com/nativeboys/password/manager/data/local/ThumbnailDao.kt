package com.nativeboys.password.manager.data.local

import androidx.room.*
import com.nativeboys.password.manager.data.ThumbnailData

@Dao
interface ThumbnailDao {

    @Query("SELECT * FROM thumbnails WHERE id = :id")
    suspend fun findById(id: String): ThumbnailData

    @Query("SELECT * FROM thumbnails")
    suspend fun findAll(): List<ThumbnailData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(thumbnails: List<ThumbnailData>)

    @Delete
    suspend fun delete(thumbnails: List<ThumbnailData>)

    @Update
    suspend fun update(thumbnails: List<ThumbnailData>)

}