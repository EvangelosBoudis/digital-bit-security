package com.nativeboys.password.manager.data

import java.sql.Timestamp
import java.util.*

object MockData {

    val thumbnails: List<ThumbnailData>
    val categories: List<CategoryData>
    val fields: List<FieldData>
    val fieldsContent: List<FieldContentModel>
    val items: List<ItemData>

    val thumbnailsUrls: List<String>
    val thumbnailsModels: List<ThumbnailModel>
    val thumbnailsWithAdds: List<ThumbnailModel>

    val filters: List<FilterModel>
    val tags: List<TagModel>
    val tagsWithAdd: List<TagModel>

    init {

        val ownerId = UUID.randomUUID().toString()

        val appleThumb = ThumbnailData("https://imagens.canaltech.com.br/empresas/838.400.jpg")
        val adobeThumb = ThumbnailData("https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg")
        val behanceThumb = ThumbnailData("https://dist.neo4j.com/wp-content/uploads/20180720064210/belogo-social-posts-default.png")
        val dribbleThumb = ThumbnailData("https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png")
        val facebookThumb = ThumbnailData("https://seeklogo.com/images/F/facebook-icon-logo-C61047A9E7-seeklogo.com.png")
        val instagramThumb = ThumbnailData("https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg")
        val youtubeThumb = ThumbnailData("https://i.pinimg.com/originals/83/0e/e1/830ee1e682300a661955fce4011bf9b3.jpg")

        val gamingCategory = CategoryData(name = "Games", thumbnailUrl = "GAMEPAD", ownerId = "George", defaultCategory = true)
        val subChannelCategory = CategoryData(name = "Subscription Channels", thumbnailUrl = "NETFLIX", ownerId = "George", defaultCategory = true)
        val bankCategory = CategoryData(name = "Bank", thumbnailUrl = "BANK", ownerId = "George", defaultCategory = true)
        val networkCategory = CategoryData(name = "Networks", thumbnailUrl = "NETWORK", ownerId = "George", defaultCategory = true)
        val alarmsCategory = CategoryData(name = "Alarms", thumbnailUrl = "ALARM", ownerId = "George", defaultCategory = true)
        val homeCategory = CategoryData(name = "Home", thumbnailUrl = "HOME", ownerId = "George", defaultCategory = true)
        val wordPressCategory = CategoryData(name = "Wordpress", thumbnailUrl = "WORDPRESS", ownerId = "George", defaultCategory = true)
        val accessPointsCategory = CategoryData(name = "Access Points", thumbnailUrl = "ACCESS_POINT", ownerId = "George", defaultCategory = true)
        val airportCategory = CategoryData(name = "Airport", thumbnailUrl = "AIRPORT", ownerId = "George", defaultCategory = true)
        val appleCategory = CategoryData(name = "Apple", thumbnailUrl = "APPLE", ownerId = "George", defaultCategory = true)
        val tabletCategory = CategoryData(name = "Tablets", thumbnailUrl = "TABLET", ownerId = "George", defaultCategory = true)
        val phoneCategory = CategoryData(name = "Phones", thumbnailUrl = "PHONE", ownerId = "George", defaultCategory = true)
        val bitcoinCategory = CategoryData(name = "BitCoins", thumbnailUrl = "BITCOIN", ownerId = "George", defaultCategory = true)
        val bitBucketCategory = CategoryData(name = "BitBucket", thumbnailUrl = "BITBUCKET", ownerId = "George", defaultCategory = true)
        val cloudCategory = CategoryData(name = "Cloud", thumbnailUrl = "CLOUD", ownerId = "George", defaultCategory = true)
        val mailCategory = CategoryData(name = "Mail", thumbnailUrl = "MAILBOX", ownerId = "George", defaultCategory = true)
        val desktopCategory = CategoryData(name = "Desktop Mac", thumbnailUrl = "DESKTOP_MAC", ownerId = "George", defaultCategory = true)
        val laptopCategory = CategoryData(name = "Laptop Windows", thumbnailUrl = "LAPTOP_WINDOWS", ownerId = "George", defaultCategory = true)
        val towerCategory = CategoryData(name = "Desktop Tower", thumbnailUrl = "DESKTOP_TOWER", ownerId = "George", defaultCategory = true)
        val remoteCategory = CategoryData(name = "Remote Desktop", thumbnailUrl = "REMOTE_DESKTOP", ownerId = "George", defaultCategory = true)

        val appleId = ItemData(name = "Apple ID", description = "Ritsa's first account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailUrl = appleThumb.url, dateModified = Timestamp(System.currentTimeMillis()),
            requiresPassword = false, favorite = true, categoryId = appleCategory.id, ownerId = ownerId)
        val adobe = ItemData(name = "Adobe", description = "ZeusTech account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailUrl = adobeThumb.url, dateModified = Timestamp(System.currentTimeMillis()),
            requiresPassword = false, favorite = true, categoryId = cloudCategory.id, ownerId = ownerId)
        val behance = ItemData(name = "Behance", description = "George's account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailUrl = behanceThumb.url, dateModified = Timestamp(System.currentTimeMillis()),
            requiresPassword = false, favorite = false, categoryId = cloudCategory.id, ownerId = ownerId)
        val dribble = ItemData(name = "Dribbble", description = "Development account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailUrl = dribbleThumb.url, dateModified = Timestamp(System.currentTimeMillis()),
            requiresPassword = false, favorite = false, categoryId = cloudCategory.id, ownerId = ownerId)
        val facebook = ItemData(name = "Facebook", description = "My personal account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailUrl = facebookThumb.url, dateModified = Timestamp(System.currentTimeMillis()),
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)
        val instagram = ItemData(name = "Instagram", description = "My personal account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailUrl = instagramThumb.url, dateModified = Timestamp(System.currentTimeMillis()),
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)
        val youtube = ItemData(name = "Youtube", description = "Main Gmail", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailUrl = youtubeThumb.url, dateModified = Timestamp(System.currentTimeMillis()),
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)

        val field1 = FieldContentModel(name = "Website", type = 1, hidden = false, content = "Instagram")
        val field2 = FieldContentModel(name = "Email/Username", type = 1, hidden = false, content = "yasirbugra@gmail.com")
        val field3 = FieldContentModel(name = "Password", type = 1, hidden = true, content = "Yasir123%'/PzhL921")

        val f1 = FieldData(name = "WebSite", type = 1, hidden = true, categoryId = desktopCategory.id)
        val f2 = FieldData(name = "Email/Username", type = 2, hidden = false, categoryId = desktopCategory.id)
        val f3 = FieldData(name = "Password", type = 2, hidden = true, categoryId = desktopCategory.id)

        val x  = listOf(f1, f2, f3).toMutableList()
        x.add(FieldData(name = "", hidden = true, categoryId = desktopCategory.id))
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

        this.filters = filters

        val tags = listOf("Social", "Master Passwords", "Bank", "Shopping", "Personal").map {
            TagModel(it, 1)
        }
        this.tags = tags

        val tagsWithAdd = tags.toMutableList()
        tagsWithAdd.add(TagModel("", 3))
        this.tagsWithAdd = tagsWithAdd
    }

}