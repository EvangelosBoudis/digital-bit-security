package com.nativeboys.password.manager.other

import com.nativeboys.password.manager.data.*
import java.sql.Timestamp
import java.util.*

object MockData {

    val thumbnails: List<ThumbnailEntity>
    val categories: List<CategoryEntity>
    val fields: List<FieldEntity>
    val fieldsContent: List<FieldContentModel>
    val items: List<ItemEntity>

    val thumbnailsUrls: List<String>
    val thumbnailsModels: List<ThumbnailModel>
    val thumbnailsWithAdds: List<ThumbnailModel>

    val filters: List<FilterModel>
    val tags: List<TagModel>
    val tagsWithAdd: List<TagModel>

    init {

        val ownerId = UUID.randomUUID().toString()

        val appleThumb = ThumbnailEntity(url = "https://imagens.canaltech.com.br/empresas/838.400.jpg")
        val adobeThumb = ThumbnailEntity(url = "https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg")
        val behanceThumb = ThumbnailEntity(url = "https://dist.neo4j.com/wp-content/uploads/20180720064210/belogo-social-posts-default.png")
        val dribbleThumb = ThumbnailEntity(url = "https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png")
        val facebookThumb = ThumbnailEntity(url = "https://seeklogo.com/images/F/facebook-icon-logo-C61047A9E7-seeklogo.com.png")
        val instagramThumb = ThumbnailEntity(url = "https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg")
        val youtubeThumb = ThumbnailEntity(url = "https://i.pinimg.com/originals/83/0e/e1/830ee1e682300a661955fce4011bf9b3.jpg")

        val gamingCategory = CategoryEntity(name = "Games", thumbnailCode = "GAMEPAD", ownerId = "George")
        val subChannelCategory = CategoryEntity(name = "Subscription Channels", thumbnailCode = "NETFLIX", ownerId = "George")
        val bankCategory = CategoryEntity(name = "Bank", thumbnailCode = "BANK", ownerId = "George")
        val networkCategory = CategoryEntity(name = "Networks", thumbnailCode = "NETWORK", ownerId = "George")
        val alarmsCategory = CategoryEntity(name = "Alarms", thumbnailCode = "ALARM", ownerId = "George")
        val homeCategory = CategoryEntity(name = "Home", thumbnailCode = "HOME", ownerId = "George")
        val wordPressCategory = CategoryEntity(name = "Wordpress", thumbnailCode = "WORDPRESS", ownerId = "George")
        val accessPointsCategory = CategoryEntity(name = "Access Points", thumbnailCode = "ACCESS_POINT", ownerId = "George")
        val airportCategory = CategoryEntity(name = "Airport", thumbnailCode = "AIRPORT", ownerId = "George")
        val appleCategory = CategoryEntity(name = "Apple", thumbnailCode = "APPLE", ownerId = "George")
        val tabletCategory = CategoryEntity(name = "Tablets", thumbnailCode = "TABLET", ownerId = "George")
        val phoneCategory = CategoryEntity(name = "Phones", thumbnailCode = "PHONE", ownerId = "George")
        val bitcoinCategory = CategoryEntity(name = "BitCoins", thumbnailCode = "BITCOIN", ownerId = "George")
        val bitBucketCategory = CategoryEntity(name = "BitBucket", thumbnailCode = "BITBUCKET", ownerId = "George")
        val cloudCategory = CategoryEntity(name = "Cloud", thumbnailCode = "CLOUD", ownerId = "George")
        val mailCategory = CategoryEntity(name = "Mail", thumbnailCode = "MAILBOX", ownerId = "George")
        val desktopCategory = CategoryEntity(name = "Desktop Mac", thumbnailCode = "DESKTOP_MAC", ownerId = "George")
        val laptopCategory = CategoryEntity(name = "Laptop Windows", thumbnailCode = "LAPTOP_WINDOWS", ownerId = "George")
        val towerCategory = CategoryEntity(name = "Desktop Tower", thumbnailCode = "DESKTOP_TOWER", ownerId = "George")
        val remoteCategory = CategoryEntity(name = "Remote Desktop", thumbnailCode = "REMOTE_DESKTOP", ownerId = "George")

        val appleId = ItemEntity(name = "Apple ID", description = "Ritsa's first account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = appleThumb.url,
            requiresPassword = false, favorite = true, categoryId = appleCategory.id, ownerId = ownerId)
        val adobe = ItemEntity(name = "Adobe", description = "ZeusTech account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = adobeThumb.url,
            requiresPassword = false, favorite = true, categoryId = cloudCategory.id, ownerId = ownerId)
        val behance = ItemEntity(name = "Behance", description = "George's account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = behanceThumb.url,
            requiresPassword = false, favorite = false, categoryId = cloudCategory.id, ownerId = ownerId)
        val dribble = ItemEntity(name = "Dribbble", description = "Development account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = dribbleThumb.url,
            requiresPassword = false, favorite = false, categoryId = cloudCategory.id, ownerId = ownerId)
        val facebook = ItemEntity(name = "Facebook", description = "My personal account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = facebookThumb.url,
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)
        val instagram = ItemEntity(name = "Instagram", description = "My personal account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = instagramThumb.url,
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)
        val youtube = ItemEntity(name = "Youtube", description = "Main Gmail", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = youtubeThumb.url,
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)

        val field1 = FieldContentModel(name = "Website", type = 1, hidden = false, content = "Instagram")
        val field2 = FieldContentModel(name = "Email/Username", type = 1, hidden = false, content = "yasirbugra@gmail.com")
        val field3 = FieldContentModel(name = "Password", type = 1, hidden = true, content = "Yasir123%'/PzhL921")

        val f1 = FieldEntity(name = "WebSite", type = 1, categoryId = desktopCategory.id)
        val f2 = FieldEntity(name = "Email/Username", type = 2, categoryId = desktopCategory.id)
        val f3 = FieldEntity(name = "Password", type = 2, categoryId = desktopCategory.id)

        val x  = listOf(f1, f2, f3).toMutableList()
        x.add(FieldEntity(name = "", categoryId = desktopCategory.id))
        fields = x

        thumbnails = listOf(appleThumb, adobeThumb, behanceThumb, dribbleThumb, facebookThumb, instagramThumb, youtubeThumb)

        categories = listOf(gamingCategory, subChannelCategory, bankCategory, networkCategory, alarmsCategory,
            homeCategory, wordPressCategory, accessPointsCategory, airportCategory, appleCategory,
            tabletCategory, phoneCategory, bitcoinCategory, bitBucketCategory, cloudCategory,
            mailCategory, desktopCategory, laptopCategory, towerCategory, remoteCategory)

        fieldsContent = listOf(field1, field2, field3)

        items = listOf(appleId, adobe, behance, dribble, facebook, instagram, youtube)

        thumbnailsUrls = thumbnails.map { it.url }

        thumbnailsModels = thumbnailsUrls.mapIndexed { index, model ->
            val type = if (index % 2 == 0) 1 else 2
            ThumbnailModel(
                model,
                type
            )
        }
        val ath = thumbnailsModels.toMutableList()
        ath.add(ThumbnailModel(type = 3))
        thumbnailsWithAdds = ath

        val filters = categories.map {
            FilterModel(it.id, it.name, false)
        }.toMutableList()
        filters.add(0, FilterModel("FAVORITES", "Favorites", true))

        MockData.filters = filters

        val tags = listOf("Social", "Master Passwords", "Bank", "Shopping", "Personal").map {
            TagModel(it, 1)
        }
        MockData.tags = tags

        val tagsWithAdd = tags.toMutableList()
        tagsWithAdd.add(TagModel("", 3))
        MockData.tagsWithAdd = tagsWithAdd
    }

}