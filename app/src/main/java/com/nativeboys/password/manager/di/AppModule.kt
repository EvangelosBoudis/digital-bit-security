package com.nativeboys.password.manager.di

import android.app.Application
import androidx.room.Room
import com.github.leonardoxh.keystore.CipherStorage
import com.github.leonardoxh.keystore.CipherStorageFactory
import com.nativeboys.password.manager.BuildConfig.DATABASE_ENCRYPTION_KEY_ALIAS
import com.nativeboys.password.manager.BuildConfig.DATABASE_NAME
import com.nativeboys.password.manager.data.storage.*
import com.nativeboys.password.manager.data.preferences.PreferencesManager
import com.nativeboys.password.manager.data.preferences.PreferencesManagerImpl
import com.nativeboys.password.manager.util.encryptIfAliasNotExist
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ApplicationComponent Deprecated
object AppModule {

    @Provides
    @Singleton
    fun provideCipherStorage(app: Application): CipherStorage {
        return CipherStorageFactory.newInstance(app)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(app: Application): PreferencesManager {
        return PreferencesManagerImpl(app)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        cipherStorage: CipherStorage
    ): AppDatabase {
        val key = cipherStorage.encryptIfAliasNotExist(
            DATABASE_ENCRYPTION_KEY_ALIAS,
            UUID.randomUUID().toString()
        )
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .openHelperFactory(SupportFactory(SQLiteDatabase.getBytes(key)))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Singleton
    @Provides
    fun provideFieldDao(db: AppDatabase): FieldDao {
        return db.fieldDao()
    }

    @Singleton
    @Provides
    fun provideThumbnailDao(db: AppDatabase): ThumbnailDao {
        return db.thumbnailDao()
    }

    @Singleton
    @Provides
    fun provideItemDao(db: AppDatabase): ItemDao {
        return db.itemDao()
    }

    @Singleton
    @Provides
    fun provideContentDao(db: AppDatabase): ContentDao {
        return db.contentDao()
    }

/*    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())*/

}