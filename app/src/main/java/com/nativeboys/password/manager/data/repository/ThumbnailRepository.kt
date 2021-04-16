package com.nativeboys.password.manager.data.repository

import androidx.room.Transaction
import com.nativeboys.password.manager.data.ThumbnailData
import com.nativeboys.password.manager.data.local.ItemDao
import com.nativeboys.password.manager.data.local.ThumbnailDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThumbnailRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val thumbnailDao: ThumbnailDao
) {

    @Transaction
    suspend fun replaceAllThumbnails(thumbnails: List<ThumbnailData>) {
        val existingThumbnails = thumbnailDao.findAll()
        thumbnailDao.delete(
            existingThumbnails.filter { existing ->
                thumbnails.indexOfFirst { existing.id == it.id } == -1
            }
        )
        thumbnailDao.save(
            thumbnails.filter { thumbnail ->
                existingThumbnails.indexOfFirst { it.id == thumbnail.id } == -1
            }
        )
        thumbnailDao.update(
            thumbnails.filter { thumbnail ->
                existingThumbnails.indexOfFirst { it.id == thumbnail.id } == 1
            }
        )
    }

}

