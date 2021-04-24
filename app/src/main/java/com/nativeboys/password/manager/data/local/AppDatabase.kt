package com.nativeboys.password.manager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nativeboys.password.manager.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [UserData::class, ThumbnailData::class, CategoryData::class, FieldData::class, ItemData::class, ContentData::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun fieldDao(): FieldDao
    abstract fun thumbnailDao(): ThumbnailDao
    abstract fun itemDao(): ItemDao
    abstract fun contentDao(): ContentDao

    class Callback @Inject constructor(
            private val database: Provider<AppDatabase>,
            private val applicationScope: CoroutineScope
        ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            mockData()
        }

        // TODO: Remove
        private fun mockData() {
            val categoryDao = database.get().categoryDao()
            val fieldDao = database.get().fieldDao()
            val thumbnailDao = database.get().thumbnailDao()
            val itemDao = database.get().itemDao()
            val contentDao = database.get().contentDao()

            val userId = UUID.randomUUID().toString()

            // Thumbnails
            val appleThumb = ThumbnailData(url = "https://imagens.canaltech.com.br/empresas/838.400.jpg")
            val adobeThumb = ThumbnailData(url = "https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg")
            val behanceThumb = ThumbnailData(url = "https://dist.neo4j.com/wp-content/uploads/20180720064210/belogo-social-posts-default.png")
            val dribbleThumb = ThumbnailData(url = "https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png")
            val facebookThumb = ThumbnailData(url = "https://seeklogo.com/images/F/facebook-icon-logo-C61047A9E7-seeklogo.com.png")
            val instagramThumb = ThumbnailData(url = "https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg")
            val youtubeThumb = ThumbnailData(url = "https://i.pinimg.com/originals/83/0e/e1/830ee1e682300a661955fce4011bf9b3.jpg")
            val modemThumb = ThumbnailData(url = "https://www.e-wireless.gr/images/thumbnails/1143/1000/detailed/266/TD-W9970_EU_2.0_01_large_1511246802368o.jpg")
            val euroBankThumb = ThumbnailData(url = "https://1000logos.net/wp-content/uploads/2020/04/Logo-Eurobank.jpg")
            val thumbnails = listOf(appleThumb, adobeThumb, behanceThumb, behanceThumb, dribbleThumb, facebookThumb, instagramThumb, youtubeThumb, modemThumb, euroBankThumb)

            // Categories
            val accountCategory = CategoryData(name = "Accounts", thumbnailCode = "ACCOUNT", ownerId = "ADMIN")
            val creditCategory = CategoryData(name = "Credit Cards", thumbnailCode = "CREDIT_CARD", ownerId = "ADMIN")
            val networkCategory = CategoryData(name = "Networks", thumbnailCode = "NETWORK", ownerId = "ADMIN")
            val categories = listOf(accountCategory, creditCategory, networkCategory)

            // Fields
            val accountF1 = FieldData(name = "E-Mail", type = "textEmailAddress", categoryId = accountCategory.id)
            val accountF2 = FieldData(name = "Username", type = "text", categoryId = accountCategory.id)
            val accountF3 = FieldData(name = "Password", type = "textPassword", categoryId = accountCategory.id)
            val accountF4 = FieldData(name = "Phone number", type = "phone", categoryId = accountCategory.id)
            val accountF5 = FieldData(name = "Password hint", type = "text", categoryId = accountCategory.id)

            val creditF1 = FieldData(name = "Card holder name", type = "text", categoryId = creditCategory.id)
            val creditF2 = FieldData(name = "Card Number", type = "number", categoryId = creditCategory.id)
            val creditF3 = FieldData(name = "Expiration date", type = "date", categoryId = creditCategory.id)
            val creditF4 = FieldData(name = "CVV", type = "textPassword", categoryId = creditCategory.id)

            val networkF1 = FieldData(name = "IP address", type = "text", categoryId = networkCategory.id)
            val networkF2 = FieldData(name = "Gateway", type = "text", categoryId = networkCategory.id)
            val networkF3 = FieldData(name = "Prefix length", type = "number", categoryId = networkCategory.id)
            val networkF4 = FieldData(name = "DNS 1", type = "text", categoryId = networkCategory.id)
            val networkF5 = FieldData(name = "DNS 2", type = "text", categoryId = networkCategory.id)
            val fields = listOf(accountF1, accountF2, accountF3, accountF4, accountF5, creditF1, creditF2, creditF3, creditF4, networkF1, networkF2, networkF3, networkF4, networkF5)

            // Items
            val appleId = ItemData(name = "Apple ID", description = "Ritsa's account", notes = "This account is linked to iPad", tags = "Apple,iPad,Tablet,Ritsa",
                thumbnailId = appleThumb.id, requiresPassword = true, favorite = true, categoryId = accountCategory.id, ownerId = userId)
            val adobe = ItemData(name = "Adobe", description = "ZeusTech account", notes = "This account handled by Fuk Sua", tags = "ZeusTech,Prototype,User Interface",
                thumbnailId = adobeThumb.id, requiresPassword = false, favorite = false, categoryId = accountCategory.id, ownerId = userId)
            val behance = ItemData(name = "Behance", description = "Family account", notes = "This account contains photos from travels that we had",
                tags = "Art,Photos,Hobby", thumbnailId = behanceThumb.id, requiresPassword = false, favorite = true, categoryId = accountCategory.id, ownerId = userId)
            val dribble = ItemData(name = "Dribbble", description = "Development account", notes = "This account contains a bunch of libraries with awesome mobile user interfaces", tags = "Inspiration,User Interface,Development",
                thumbnailId = dribbleThumb.id, requiresPassword = false, favorite = true, categoryId = accountCategory.id, ownerId = userId)
            val facebook = ItemData(name = "Facebook", description = "My account", notes = "", tags = "Social Media,Chat,Video Call",
                thumbnailId = facebookThumb.id, requiresPassword = true, favorite = false, categoryId = accountCategory.id, ownerId = userId)
            val instagram = ItemData(name = "Instagram", description = "Shared account", notes = "", tags = "Social Media,Chat,Video Call",
                thumbnailId = instagramThumb.id, requiresPassword = false, favorite = false, categoryId = accountCategory.id, ownerId = userId)
            val hzTest = ItemData(name = "HZ-TEST", description = "Network details", notes = "", tags = "ZeusTech,Wifi,Network",
                thumbnailId = modemThumb.id, requiresPassword = false, favorite = true, categoryId = networkCategory.id, ownerId = userId)
            val euroBank = ItemData(name = "Eurobank", description = "Credit card details", notes = "", tags = "Eurobank,Payment,Money",
                thumbnailId = euroBankThumb.id, requiresPassword = true, favorite = false, categoryId = creditCategory.id, ownerId = userId)

            val items = listOf(appleId, adobe, behance, dribble, facebook, instagram, hzTest, euroBank)

            // ItemFields
            val appleF1 = ContentData(fieldId = accountF1.id, itemId = appleId.id, content = "ritsa.galanaki@apple.com")
            val appleF2 = ContentData(fieldId = accountF2.id, itemId = appleId.id, content = "RitsaGalanaki")
            val appleF3 = ContentData(fieldId = accountF3.id, itemId = appleId.id, content = "Yasir123%'/PzhL921")
            val appleF4 = ContentData(fieldId = accountF4.id, itemId = appleId.id, content = "6909004881")
            val appleF5 = ContentData(fieldId = accountF5.id, itemId = appleId.id, content = "When we met with Vaggelis")

            val adobeF1 = ContentData(fieldId = accountF1.id, itemId = adobe.id, content = "george.mulianakis@zeustech.com")
            val adobeF2 = ContentData(fieldId = accountF2.id, itemId = adobe.id, content = "GeorgeMulianakis")
            val adobeF3 = ContentData(fieldId = accountF3.id, itemId = adobe.id, content = "Yasir123%'/PzhL921")
            val adobeF4 = ContentData(fieldId = accountF4.id, itemId = adobe.id, content = "6909004882")
            val adobeF5 = ContentData(fieldId = accountF5.id, itemId = adobe.id, content = "")

            val behanceF1 = ContentData(fieldId = accountF1.id, itemId = behance.id, content = "boudis.family@gmail.com")
            val behanceF2 = ContentData(fieldId = accountF2.id, itemId = behance.id, content = "BoudisFamily")
            val behanceF3 = ContentData(fieldId = accountF3.id, itemId = behance.id, content = "Yasir123%'/PzhL921")
            val behanceF4 = ContentData(fieldId = accountF4.id, itemId = behance.id, content = "6909004880")
            val behanceF5 = ContentData(fieldId = accountF5.id, itemId = behance.id, content = "When we met with Ritsa")

            val dribbleF1 = ContentData(fieldId = accountF1.id, itemId = dribble.id, content = "v.boudis1995@gmail.com")
            val dribbleF2 = ContentData(fieldId = accountF2.id, itemId = dribble.id, content = "Vaggelis")
            val dribbleF3 = ContentData(fieldId = accountF3.id, itemId = dribble.id, content = "Yasir123%'/PzhL921")
            val dribbleF4 = ContentData(fieldId = accountF4.id, itemId = dribble.id, content = "6909004880")
            val dribbleF5 = ContentData(fieldId = accountF5.id, itemId = dribble.id, content = "When we met with Ritsa")

            val facebookF1 = ContentData(fieldId = accountF1.id, itemId = facebook.id, content = "vaggelis.boudis1995@gmail.com")
            val facebookF2 = ContentData(fieldId = accountF2.id, itemId = facebook.id, content = "VaggelisBoudis")
            val facebookF3 = ContentData(fieldId = accountF3.id, itemId = facebook.id, content = "Yasir123%'/PzhL921")
            val facebookF4 = ContentData(fieldId = accountF4.id, itemId = facebook.id, content = "6909004880")
            val facebookF5 = ContentData(fieldId = accountF5.id, itemId = facebook.id, content = "When we f*** with Ritsa")

            val instagramF1 = ContentData(fieldId = accountF1.id, itemId = instagram.id, content = "boudis.family@gmail.com")
            val instagramF2 = ContentData(fieldId = accountF2.id, itemId = instagram.id, content = "BoudisFamily")
            val instagramF3 = ContentData(fieldId = accountF3.id, itemId = instagram.id, content = "Yasir123%'/PzhL921")
            val instagramF4 = ContentData(fieldId = accountF4.id, itemId = instagram.id, content = "6909004880")
            val instagramF5 = ContentData(fieldId = accountF5.id, itemId = instagram.id, content = "When we f*** with Ritsa")

            val hzTestF1 = ContentData(fieldId = networkF1.id, itemId = hzTest.id, content = "172.16.1.111")
            val hzTestF2 = ContentData(fieldId = networkF2.id, itemId = hzTest.id, content = "172.16.1.1")
            val hzTestF3 = ContentData(fieldId = networkF3.id, itemId = hzTest.id, content = "24")
            val hzTestF4 = ContentData(fieldId = networkF4.id, itemId = hzTest.id, content = "172.16.1.20")
            val hzTestF5 = ContentData(fieldId = networkF5.id, itemId = hzTest.id, content = "8.8.4.4")

            val euroBankF1 = ContentData(fieldId = creditF1.id, itemId = euroBank.id, content = "JohnDoe")
            val euroBankF2 = ContentData(fieldId = creditF2.id, itemId = euroBank.id, content = "1234-5678-910-1213")
            val euroBankF3 = ContentData(fieldId = creditF3.id, itemId = euroBank.id, content = "10/20")
            val euroBankF4 = ContentData(fieldId = creditF4.id, itemId = euroBank.id, content = "123")

            val itemFields = listOf(
                appleF1, appleF2, appleF3, appleF4, appleF5,
                adobeF1, adobeF2, adobeF3, adobeF4, adobeF5,
                behanceF1, behanceF2, behanceF3, behanceF4, behanceF5,
                dribbleF1, dribbleF2, dribbleF3, dribbleF4, dribbleF5,
                facebookF1, facebookF2 , facebookF3, facebookF4, facebookF5,
                instagramF1, instagramF2, instagramF3, instagramF4, instagramF5,
                hzTestF1, hzTestF2, hzTestF3, hzTestF4, hzTestF5,
                euroBankF1, euroBankF2, euroBankF3, euroBankF4
            )

            applicationScope.launch {
                thumbnailDao.saveOrReplace(thumbnails)
                categoryDao.save(categories)
                fieldDao.save(fields)
                itemDao.save(items)
                contentDao.save(itemFields)
            }
        }

    }

}
