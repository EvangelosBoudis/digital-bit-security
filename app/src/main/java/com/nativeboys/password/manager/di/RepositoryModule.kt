package com.nativeboys.password.manager.di

import com.nativeboys.password.manager.data.repositories.category.CategoryRepository
import com.nativeboys.password.manager.data.repositories.category.CategoryRepositoryImpl
import com.nativeboys.password.manager.data.repositories.fields.FieldRepository
import com.nativeboys.password.manager.data.repositories.fields.FieldRepositoryImpl
import com.nativeboys.password.manager.data.repositories.items.ItemRepository
import com.nativeboys.password.manager.data.repositories.items.ItemRepositoryImpl
import com.nativeboys.password.manager.data.repositories.thumbnails.ThumbnailRepository
import com.nativeboys.password.manager.data.repositories.thumbnails.ThumbnailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindFieldRepository(
        fieldRepositoryImpl: FieldRepositoryImpl
    ): FieldRepository

    @Binds
    @Singleton
    abstract fun bindItemRepository(
        itemRepositoryImpl: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    @Singleton
    abstract fun bindThumbnailsRepository(
        thumbnailRepositoryImpl: ThumbnailRepositoryImpl
    ): ThumbnailRepository

}