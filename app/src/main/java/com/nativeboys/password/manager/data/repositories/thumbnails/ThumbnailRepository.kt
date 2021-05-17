package com.nativeboys.password.manager.data.repositories.thumbnails

import com.nativeboys.password.manager.data.ThumbnailDto

interface ThumbnailRepository {

    suspend fun replaceAllThumbnails(thumbnailDto: List<ThumbnailDto>)

}