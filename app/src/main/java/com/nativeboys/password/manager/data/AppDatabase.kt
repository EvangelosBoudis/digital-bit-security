package com.nativeboys.password.manager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun fieldDao(): FieldDao

    class Callback @Inject constructor(
            private val database: Provider<AppDatabase>,
            private val applicationScope: CoroutineScope
        ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val categoryDao = database.get().categoryDao()
            val fieldDao = database.get().fieldDao()

            // Categories
            val accountCategory = CategoryEntity(name = "Emails", thumbnailCode = "EMAIL", ownerId = "ADMIN")
            val creditCategory = CategoryEntity(name = "Credit Cards", thumbnailCode = "CREDIT_CARD", ownerId = "ADMIN")
            val networkCategory = CategoryEntity(name = "Networks", thumbnailCode = "NETWORK", ownerId = "ADMIN")
            val categories = listOf(accountCategory, creditCategory, networkCategory)

            // Fields
            val accountF1 = FieldEntity(name = "E-Mail", type = "textEmailAddress", categoryId = accountCategory.id)
            val accountF2 = FieldEntity(name = "Username", type = "text", categoryId = accountCategory.id)
            val accountF3 = FieldEntity(name = "Password", type = "textPassword", categoryId = accountCategory.id)
            val accountF4 = FieldEntity(name = "Phone number", type = "phone", categoryId = accountCategory.id)
            val accountF5 = FieldEntity(name = "Password hint", type = "text", categoryId = accountCategory.id)

            val creditF1 = FieldEntity(name = "Card holder name", type = "text", categoryId = creditCategory.id)
            val creditF2 = FieldEntity(name = "CVV", type = "textPassword", categoryId = creditCategory.id)
            val creditF3 = FieldEntity(name = "Expiration date", type = "date", categoryId = creditCategory.id)

            val networkF1 = FieldEntity(name = "IP address", type = "text", categoryId = networkCategory.id)
            val networkF2 = FieldEntity(name = "Gateway", type = "text", categoryId = networkCategory.id)
            val networkF3 = FieldEntity(name = "Prefix length", type = "number", categoryId = networkCategory.id)
            val networkF4 = FieldEntity(name = "DNS 1", type = "text", categoryId = networkCategory.id)
            val networkF5 = FieldEntity(name = "DNS 2", type = "text", categoryId = networkCategory.id)
            val fields = listOf(accountF1, accountF2, accountF3, accountF4, accountF5, creditF1, creditF2, creditF3, networkF1, networkF2, networkF3, networkF4, networkF5)

            applicationScope.launch {
                categoryDao.save(categories)
                fieldDao.save(fields)
            }

        }

    }

}
