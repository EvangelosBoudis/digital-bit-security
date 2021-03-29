package com.nativeboys.password.manager.di

import android.app.Application
import androidx.room.Room
import com.nativeboys.password.manager.data.ManagerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ApplicationComponent Deprecated
object ManagerModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: ManagerDatabase.Callback
    ) = Room.databaseBuilder(app, ManagerDatabase::class.java, "item_manager_database")
        .fallbackToDestructiveMigration() // Drop table then create new one
        .addCallback(callback)
        .build()

    @Provides
    fun provideCategoryDao(db: ManagerDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

}