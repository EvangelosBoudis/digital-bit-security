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

    @Transaction
    suspend fun replaceAllThumbnails(thumbnails: List<ThumbnailDto>) {
        val transformedThumbnails = thumbnails
            .filter {
                it.id.isNotEmpty()
            }.map {
                ThumbnailData(it.id, it.url)
            }

        val prevThumbnails = thumbnailDao.findAll()

        val thumbnailsForDelete = prevThumbnails
            .filter { prev ->
                transformedThumbnails.contains {
                    prev.id == it.id
                }
            }

        val thumbnailsForSave = transformedThumbnails
            .filter { thumbnail ->
                prevThumbnails.contains { it.id == thumbnail.id }
            }

        val thumbnailsForUpdate = transformedThumbnails
            .filter { thumbnail ->
                prevThumbnails.contains { it.id == thumbnail.id }
            }

        thumbnailDao.delete(thumbnailsForDelete)
        thumbnailDao.save(thumbnailsForSave)
        thumbnailDao.update(thumbnailsForUpdate)
    }

}



