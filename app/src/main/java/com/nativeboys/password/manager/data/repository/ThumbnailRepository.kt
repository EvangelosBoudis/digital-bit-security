package com.nativeboys.password.manager.data.repository

import androidx.room.Transaction
import com.nativeboys.password.manager.data.ThumbnailData
import com.nativeboys.password.manager.data.local.ThumbnailDao
import com.nativeboys.password.manager.other.contains
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThumbnailRepository @Inject constructor(
    private val thumbnailDao: ThumbnailDao
) {

    @Transaction
    suspend fun replaceAllThumbnails(thumbnails: List<ThumbnailData>) {

        val prevThumbnails = thumbnailDao.findAll()

        thumbnailDao.delete(prevThumbnails.filter { prev ->
            thumbnails.contains { prev.id != it.id }
        })

        thumbnailDao.save(thumbnails.filter { thumbnail ->
            prevThumbnails.contains { it.id == thumbnail.id }
        })

        thumbnailDao.update(thumbnails.filter { thumbnail ->
            prevThumbnails.contains { it.id == thumbnail.id }
        })
    }

}



