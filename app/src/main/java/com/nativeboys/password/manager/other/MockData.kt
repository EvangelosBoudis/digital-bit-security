package com.nativeboys.password.manager.other

import com.nativeboys.password.manager.data.*
import java.util.*

object MockData {

    val thumbnails: List<ThumbnailData>
    val categories: List<CategoryData>
    val fields: List<FieldData>
    val fieldsContent: List<FieldContentModel>
    val items: List<ItemData>

    val thumbnailsUrls: List<String>
/*    val thumbnailsModels: List<ThumbnailDto>
    val thumbnailsWithAdds: List<ThumbnailDto>*/

    val filters: List<CategoryDto>
    val tags: List<TagDto>
    val tagsWithAdd: List<TagDto>

    init {

        val ownerId = UUID.randomUUID().toString()

        val appleThumb = ThumbnailData(url = "https://imagens.canaltech.com.br/empresas/838.400.jpg")
        val adobeThumb = ThumbnailData(url = "https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg")
        val behanceThumb = ThumbnailData(url = "https://dist.neo4j.com/wp-content/uploads/20180720064210/belogo-social-posts-default.png")
        val dribbleThumb = ThumbnailData(url = "https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png")
        val facebookThumb = ThumbnailData(url = "https://seeklogo.com/images/F/facebook-icon-logo-C61047A9E7-seeklogo.com.png")
        val instagramThumb = ThumbnailData(url = "https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg")
        val youtubeThumb = ThumbnailData(url = "https://i.pinimg.com/originals/83/0e/e1/830ee1e682300a661955fce4011bf9b3.jpg")

        val gamingCategory = CategoryData(name = "Games", thumbnailCode = "GAMEPAD", ownerId = "George")
        val subChannelCategory = CategoryData(name = "Subscription Channels", thumbnailCode = "NETFLIX", ownerId = "George")
        val bankCategory = CategoryData(name = "Bank", thumbnailCode = "BANK", ownerId = "George")
        val networkCategory = CategoryData(name = "Networks", thumbnailCode = "NETWORK", ownerId = "George")
        val alarmsCategory = CategoryData(name = "Alarms", thumbnailCode = "ALARM", ownerId = "George")
        val homeCategory = CategoryData(name = "Home", thumbnailCode = "HOME", ownerId = "George")
        val wordPressCategory = CategoryData(name = "Wordpress", thumbnailCode = "WORDPRESS", ownerId = "George")
        val accessPointsCategory = CategoryData(name = "Access Points", thumbnailCode = "ACCESS_POINT", ownerId = "George")
        val airportCategory = CategoryData(name = "Airport", thumbnailCode = "AIRPORT", ownerId = "George")
        val appleCategory = CategoryData(name = "Apple", thumbnailCode = "APPLE", ownerId = "George")
        val tabletCategory = CategoryData(name = "Tablets", thumbnailCode = "TABLET", ownerId = "George")
        val phoneCategory = CategoryData(name = "Phones", thumbnailCode = "PHONE", ownerId = "George")
        val bitcoinCategory = CategoryData(name = "BitCoins", thumbnailCode = "BITCOIN", ownerId = "George")
        val bitBucketCategory = CategoryData(name = "BitBucket", thumbnailCode = "BITBUCKET", ownerId = "George")
        val cloudCategory = CategoryData(name = "Cloud", thumbnailCode = "CLOUD", ownerId = "George")
        val mailCategory = CategoryData(name = "Mail", thumbnailCode = "MAILBOX", ownerId = "George")
        val desktopCategory = CategoryData(name = "Desktop Mac", thumbnailCode = "DESKTOP_MAC", ownerId = "George")
        val laptopCategory = CategoryData(name = "Laptop Windows", thumbnailCode = "LAPTOP_WINDOWS", ownerId = "George")
        val towerCategory = CategoryData(name = "Desktop Tower", thumbnailCode = "DESKTOP_TOWER", ownerId = "George")
        val remoteCategory = CategoryData(name = "Remote Desktop", thumbnailCode = "REMOTE_DESKTOP", ownerId = "George")

        val appleId = ItemData(name = "Apple ID", description = "Ritsa's first account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = appleThumb.url,
            requiresPassword = false, favorite = true, categoryId = appleCategory.id, ownerId = ownerId)
        val adobe = ItemData(name = "Adobe", description = "ZeusTech account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = adobeThumb.url,
            requiresPassword = false, favorite = true, categoryId = cloudCategory.id, ownerId = ownerId)
        val behance = ItemData(name = "Behance", description = "George's account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = behanceThumb.url,
            requiresPassword = false, favorite = false, categoryId = cloudCategory.id, ownerId = ownerId)
        val dribble = ItemData(name = "Dribbble", description = "Development account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = dribbleThumb.url,
            requiresPassword = false, favorite = false, categoryId = cloudCategory.id, ownerId = ownerId)
        val facebook = ItemData(name = "Facebook", description = "My personal account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = facebookThumb.url,
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)
        val instagram = ItemData(name = "Instagram", description = "My personal account", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = instagramThumb.url,
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)
        val youtube = ItemData(name = "Youtube", description = "Main Gmail", notes = "This is a note bla bla...",
            tags = "Social, Master Passwords, Bank, Shopping, Personal", thumbnailId = youtubeThumb.url,
            requiresPassword = false, favorite = false, categoryId = homeCategory.id, ownerId = ownerId)

        val field1 = FieldContentModel(name = "Website", type = "text", content = "Instagram")
        val field2 = FieldContentModel(name = "Email/Username", type = "textEmailAddress", content = "yasirbugra@gmail.com")
        val field3 = FieldContentModel(name = "Password", type = "textPassword", content = "Yasir123%'/PzhL921")
        val field4 = FieldContentModel(name = "Phone Number", type = "phone", content = "6909004880")

        val f1 = FieldData(name = "WebSite", type = "text", categoryId = desktopCategory.id)
        val f2 = FieldData(name = "Email/Username", type = "textEmailAddress", categoryId = desktopCategory.id)
        val f3 = FieldData(name = "Password", type = "textPassword", categoryId = desktopCategory.id)

        val x  = listOf(f1, f2, f3).toMutableList()
        x.add(FieldData(name = "", type = "", categoryId = desktopCategory.id))
        fields = x

        thumbnails = listOf(appleThumb, adobeThumb, behanceThumb, dribbleThumb, facebookThumb, instagramThumb, youtubeThumb)

        categories = listOf(gamingCategory, subChannelCategory, bankCategory, networkCategory, alarmsCategory,
            homeCategory, wordPressCategory, accessPointsCategory, airportCategory, appleCategory,
            tabletCategory, phoneCategory, bitcoinCategory, bitBucketCategory, cloudCategory,
            mailCategory, desktopCategory, laptopCategory, towerCategory, remoteCategory)

        fieldsContent = listOf(field1, field2, field3, field4)

        items = listOf(appleId, adobe, behance, dribble, facebook, instagram, youtube)

        thumbnailsUrls = thumbnails.map { it.url }

/*        thumbnailsModels = thumbnailsUrls.mapIndexed { index, model ->
            val type = if (index % 2 == 0) 1 else 2
            ThumbnailDto(
                model,
                type
            )
        }
        val ath = thumbnailsModels.toMutableList()
        ath.add(ThumbnailDto(type = 3))*/
/*        thumbnailsWithAdds = ath*/

        val filters = categories.map {
            CategoryDto(it.id, it.name, false)
        }.toMutableList()
        filters.add(0, CategoryDto("FAVORITES", "Favorites", true))

        MockData.filters = filters

        val tags = listOf("Social", "Master Passwords", "Bank", "Shopping", "Personal").map {
            TagDto(it, 1)
        }
        MockData.tags = tags

        val tagsWithAdd = tags.toMutableList()
        tagsWithAdd.add(TagDto("", 3))
        MockData.tagsWithAdd = tagsWithAdd
    }

}