package com.nativeboys.password.manager.data.repository

import androidx.room.withTransaction
import com.nativeboys.password.manager.data.ThumbnailData
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.data.local.AppDatabase
import com.nativeboys.password.manager.data.local.ThumbnailDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThumbnailRepository @Inject constructor(
    private val database: AppDatabase,
    private val thumbnailDao: ThumbnailDao
) {

    suspend fun replaceAllThumbnails(thumbnailDto: List<ThumbnailDto>) {

        val thumbnails = thumbnailDto
            .map { ThumbnailData(it.id, it.url) }

        database.withTransaction {
            thumbnailDao.deleteAllExcept(thumbnails.map { it.id })
            thumbnailDao.saveOrReplace(thumbnails)
        }

    }

}



