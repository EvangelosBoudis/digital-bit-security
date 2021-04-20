package com.nativeboys.password.manager.data.repository

import androidx.room.withTransaction
import com.nativeboys.password.manager.data.ThumbnailData
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.data.local.AppDatabase
import com.nativeboys.password.manager.data.local.ThumbnailDao
import com.nativeboys.password.manager.other.contains
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThumbnailRepository @Inject constructor(
    private val database: AppDatabase,
    private val thumbnailDao: ThumbnailDao
) {

    suspend fun replaceAllThumbnails(thumbnailDto: List<ThumbnailDto>) {

        val thumbnails = thumbnailDto
            .filter { it.url.isNotEmpty() }
            .map { ThumbnailData(it.id, it.url) }

        val prevThumbnails = thumbnailDao.findAll()

        val deleteThumbnails = prevThumbnails.filter { prev ->
            thumbnails.contains { prev.id != it.id }
        }

        val saveThumbnails = thumbnails.filter { thumbnail ->
            prevThumbnails.contains { it.id != thumbnail.id }
        }

        val updateThumbnails = thumbnails.filter { thumbnail ->
            prevThumbnails.contains { it.id == thumbnail.id }
        }

        database.withTransaction {
            thumbnailDao.delete(deleteThumbnails)
            thumbnailDao.save(saveThumbnails)
            thumbnailDao.update(updateThumbnails)
        }

    }

}



