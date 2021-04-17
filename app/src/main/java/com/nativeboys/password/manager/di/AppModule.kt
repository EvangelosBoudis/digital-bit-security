package com.nativeboys.password.manager.di

import android.app.Application
import androidx.room.Room
import com.nativeboys.password.manager.BuildConfig.DATABASE_NAME
import com.nativeboys.password.manager.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ApplicationComponent Deprecated
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: AppDatabase.Callback
    ) = Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration() // Drop table then create new one
        .addCallback(callback)
        .build()

    @Provides
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao()
    
    @Provides
    fun provideFieldDao(db: AppDatabase) = db.fieldDao()

    @Provides
    fun provideThumbnailDao(db: AppDatabase) = db.thumbnailDao()

    @Provides
    fun provideItemDao(db: AppDatabase) = db.itemDao()

    @Provides
    fun provideContentDao(db: AppDatabase) = db.contentDao()

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}