package com.nativeboys.password.manager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [
    UserEntity::class,
    ThumbnailEntity::class,
    CategoryEntity::class,
    FieldEntity::class,
    ItemEntity::class,
    ItemFieldEntity::class
], version = 1)
abstract class ManagerDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    class Callback @Inject constructor(
            private val database: Provider<ManagerDatabase>,
            private val applicationScope: CoroutineScope
        ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = database.get().categoryDao()
            val mockCategories = listOf(
                CategoryEntity(name = "Games", thumbnailCode = "GAMEPAD", ownerId = "George"),
                CategoryEntity(name = "Subscription Channels", thumbnailCode = "NETFLIX", ownerId = "George"),
                CategoryEntity(name = "Bank", thumbnailCode = "BANK", ownerId = "George"),
                CategoryEntity(name = "Networks", thumbnailCode = "NETWORK", ownerId = "George"),
                CategoryEntity(name = "Alarms", thumbnailCode = "ALARM", ownerId = "George"),
                CategoryEntity(name = "Home", thumbnailCode = "HOME", ownerId = "George"),
                CategoryEntity(name = "Wordpress", thumbnailCode = "WORDPRESS", ownerId = "George"),
                CategoryEntity(name = "Access Points", thumbnailCode = "ACCESS_POINT", ownerId = "George"),
                CategoryEntity(name = "Airport", thumbnailCode = "AIRPORT", ownerId = "George"),
                CategoryEntity(name = "Apple", thumbnailCode = "APPLE", ownerId = "George"),
                CategoryEntity(name = "Tablets", thumbnailCode = "TABLET", ownerId = "George"),
                CategoryEntity(name = "Phones", thumbnailCode = "PHONE", ownerId = "George"),
                CategoryEntity(name = "BitCoins", thumbnailCode = "BITCOIN", ownerId = "George"),
                CategoryEntity(name = "BitBucket", thumbnailCode = "BITBUCKET", ownerId = "George"),
                CategoryEntity(name = "Cloud", thumbnailCode = "CLOUD", ownerId = "George"),
                CategoryEntity(name = "Mail", thumbnailCode = "MAILBOX", ownerId = "George"),
                CategoryEntity(name = "Desktop Mac", thumbnailCode = "DESKTOP_MAC", ownerId = "George"),
                CategoryEntity(name = "Laptop Windows", thumbnailCode = "LAPTOP_WINDOWS", ownerId = "George"),
                CategoryEntity(name = "Desktop Tower", thumbnailCode = "DESKTOP_TOWER", ownerId = "George"),
                CategoryEntity(name = "Remote Desktop", thumbnailCode = "REMOTE_DESKTOP", ownerId = "George")
            )
            applicationScope.launch {
                dao.save(mockCategories)
            }
        }

    }

}