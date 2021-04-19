package com.nativeboys.password.manager.data.repository

import androidx.room.Transaction
import com.nativeboys.password.manager.data.ThumbnailData
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.data.local.ThumbnailDao
import com.nativeboys.password.manager.other.contains
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThumbnailRepository @Inject constructor(
    private val thumbnailDao: ThumbnailDao
) {

    suspend fun findAllThumbnails() = thumbnailDao.findAll()

    @Transaction
    suspend fun replaceAllThumbnails(thumbnailDto: List<ThumbnailDto>) {
        val thumbnails = thumbnailDto
            .filter { it.url.isNotEmpty() }
            .map { ThumbnailData(it.id, it.url) }

        val prevThumbnails = thumbnailDao.findAll()

        thumbnailDao.delete(prevThumbnails.filter { prev ->
            thumbnails.contains { prev.id != it.id }
        })

        thumbnailDao.save(thumbnails.filter { thumbnail ->
            prevThumbnails.contains { it.id != thumbnail.id }
        })

        thumbnailDao.update(thumbnails.filter { thumbnail ->
            prevThumbnails.contains { it.id == thumbnail.id }
        })
    }

}



