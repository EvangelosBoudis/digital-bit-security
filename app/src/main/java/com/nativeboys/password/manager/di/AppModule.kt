package com.nativeboys.password.manager.di

import android.app.Application
import androidx.room.Room
import com.github.leonardoxh.keystore.CipherStorage
import com.github.leonardoxh.keystore.CipherStorageFactory
import com.nativeboys.password.manager.BuildConfig.DATABASE_ENCRYPTION_KEY_ALIAS
import com.nativeboys.password.manager.BuildConfig.DATABASE_NAME
import com.nativeboys.password.manager.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ApplicationComponent Deprecated
object AppModule {

    @Provides
    @Singleton
    fun provideCipherStorage(
        app: Application
    ) = CipherStorageFactory.newInstance(app)

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        cipherStorage: CipherStorage,
        callback: AppDatabase.Callback
    ): AppDatabase {

        val storedDatabaseEncryptionKey = cipherStorage.decrypt(DATABASE_ENCRYPTION_KEY_ALIAS)
        val newDatabaseEncryptionKey = UUID.randomUUID().toString()

        if (storedDatabaseEncryptionKey == null) {
            cipherStorage.encrypt(DATABASE_ENCRYPTION_KEY_ALIAS, newDatabaseEncryptionKey)
        }

        val databaseEncryptionKey = (storedDatabaseEncryptionKey ?: newDatabaseEncryptionKey).toCharArray()

        val factory = SupportFactory(SQLiteDatabase.getBytes(databaseEncryptionKey))

        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .openHelperFactory(factory)
            //.createFromAsset("database/pre_populate.db") -- PROBLEM--
            .fallbackToDestructiveMigration() // Drop table then create new one
            .addCallback(callback)
            .build()
    }

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