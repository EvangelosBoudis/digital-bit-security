package com.nativeboys.password.manager.data.repositories.thumbnails

import androidx.room.withTransaction
import com.nativeboys.password.manager.data.ThumbnailData
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.password.manager.data.storage.AppDatabase
import com.nativeboys.password.manager.data.storage.ThumbnailDao
import javax.inject.Inject

class ThumbnailRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val thumbnailDao: ThumbnailDao
): ThumbnailRepository {

    override suspend fun replaceAllThumbnails(thumbnailDto: List<ThumbnailDto>) {

        val thumbnails = thumbnailDto
            .map { ThumbnailData(it.id, it.url) }

        db.withTransaction {
            thumbnailDao.deleteAllExcept(thumbnails.map { it.id })
            thumbnailDao.saveOrReplace(thumbnails)
        }

    }

}



