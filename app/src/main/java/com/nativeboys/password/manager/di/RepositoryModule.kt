package com.nativeboys.password.manager.di

import com.nativeboys.password.manager.data.storage.*
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.data.repositories.category.CategoryRepository
import com.nativeboys.password.manager.data.repositories.category.CategoryRepositoryImpl
import com.nativeboys.password.manager.data.repositories.fields.FieldRepository
import com.nativeboys.password.manager.data.repositories.fields.FieldRepositoryImpl
import com.nativeboys.password.manager.data.repositories.items.ItemRepository
import com.nativeboys.password.manager.data.repositories.items.ItemRepositoryImpl
import com.nativeboys.password.manager.data.repositories.thumbnails.ThumbnailRepository
import com.nativeboys.password.manager.data.repositories.thumbnails.ThumbnailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCategoryRepository(
        db: AppDatabase,
        categoryDao: CategoryDao,
        fieldDao: FieldDao,
        preferencesManager: PreferencesManager
    ): CategoryRepository {
        return CategoryRepositoryImpl(db, categoryDao, fieldDao, preferencesManager)
    }

    @Singleton
    @Provides
    fun provideFieldRepository(fieldDao: FieldDao): FieldRepository {
        return FieldRepositoryImpl(fieldDao)
    }

    @Singleton
    @Provides
    fun provideItemRepository(
        db: AppDatabase,
        itemDao: ItemDao,
        fieldDao: FieldDao,
        contentDao: ContentDao,
        thumbnailDao: ThumbnailDao,
        preferences: PreferencesManager
    ): ItemRepository {
        return ItemRepositoryImpl(db, itemDao, fieldDao, contentDao, thumbnailDao, preferences)
    }

    @Singleton
    @Provides
    fun provideThumbnailsRepository(
        db: AppDatabase,
        thumbnailDao: ThumbnailDao,
    ): ThumbnailRepository {
        return ThumbnailRepositoryImpl(db, thumbnailDao)
    }

}