package com.nativeboys.password.manager.data

object MockData {

    val items: List<ItemModel>

    val thumbnailsUrls: List<String>
    val thumbnails: List<ThumbnailModel>
    val thumbnailsWithAdds: List<ThumbnailModel>

    val categories: List<CategoryData>
    val fieldsContent: List<FieldContentModel>
    val filters: List<FilterModel>
    val tags: List<TagModel>
    val tagsWithAdd: List<TagModel>

    init {

        val appleThumb = "https://imagens.canaltech.com.br/empresas/838.400.jpg"
        val adobeThumb = "https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg"
        val behanceThumb = "https://dist.neo4j.com/wp-content/uploads/20180720064210/belogo-social-posts-default.png"
        val dribbleThumb = "https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png"
        val facebookThumb = "https://seeklogo.com/images/F/facebook-icon-logo-C61047A9E7-seeklogo.com.png"
        val instagramThumb = "https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg"
        val youtubeThumb = "https://i.pinimg.com/originals/83/0e/e1/830ee1e682300a661955fce4011bf9b3.jpg"

        val appleId = ItemModel(
            webSite = "Apple ID",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "This is a note bla bla...",
            tagIds = "",
            thumbnailUrl = appleThumb
        )
        val adobe = ItemModel(
            webSite = "Adobe",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "This is a note bla bla...",
            tagIds = "",
            thumbnailUrl = adobeThumb
        )
        val behance = ItemModel(
            webSite = "Behance",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "This is a note bla bla...",
            tagIds = "",
            thumbnailUrl = behanceThumb
        )
        val dribble = ItemModel(
            webSite = "Dribbble",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "This is a note bla bla...",
            tagIds = "",
            thumbnailUrl = dribbleThumb
        )
        val facebook = ItemModel(
            webSite = "Facebook",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "This is a note bla bla...",
            tagIds = "",
            thumbnailUrl = facebookThumb
        )
        val instagram = ItemModel(
            webSite = "Instagram",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "This is a note bla bla...",
            tagIds = "",
            thumbnailUrl = instagramThumb
        )
        val youtube = ItemModel(
            webSite = "Youtube",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "This is a note bla bla...",
            tagIds = "",
            thumbnailUrl = youtubeThumb
        )

        val gamingCategory = CategoryData(
            name = "Games",
            thumbnailUrl = "GAMEPAD",
            ownerId = "George",
            defaultCategory = true
        )
        val subChannelCategory = CategoryData(
            name = "Subscription Channels",
            thumbnailUrl = "NETFLIX",
            ownerId = "George",
            defaultCategory = true
        )
        val bankCategory = CategoryData(
            name = "Bank",
            thumbnailUrl = "BANK",
            ownerId = "George",
            defaultCategory = true
        )
        val networkCategory = CategoryData(
            name = "Networks",
            thumbnailUrl = "NETWORK",
            ownerId = "George",
            defaultCategory = true
        )
        val alarmsCategory = CategoryData(
            name = "Alarms",
            thumbnailUrl = "ALARM",
            ownerId = "George",
            defaultCategory = true
        )
        val homeCategory = CategoryData(
            name = "Home",
            thumbnailUrl = "HOME",
            ownerId = "George",
            defaultCategory = true
        )
        val wordPressCategory = CategoryData(
            name = "Wordpress",
            thumbnailUrl = "WORDPRESS",
            ownerId = "George",
            defaultCategory = true
        )
        val accessPointsCategory = CategoryData(
            name = "Access Points",
            thumbnailUrl = "ACCESS_POINT",
            ownerId = "George",
            defaultCategory = true
        )
        val airportCategory = CategoryData(
            name = "Airport",
            thumbnailUrl = "AIRPORT",
            ownerId = "George",
            defaultCategory = true
        )
        val appleCategory = CategoryData(
            name = "Apple",
            thumbnailUrl = "APPLE",
            ownerId = "George",
            defaultCategory = true
        )
        val tabletCategory = CategoryData(
            name = "Tablets",
            thumbnailUrl = "TABLET",
            ownerId = "George",
            defaultCategory = true
        )
        val phoneCategory = CategoryData(
            name = "Phones",
            thumbnailUrl = "PHONE",
            ownerId = "George",
            defaultCategory = true
        )
        val bitcoinCategory = CategoryData(
            name = "BitCoins",
            thumbnailUrl = "BITCOIN",
            ownerId = "George",
            defaultCategory = true
        )
        val bitBucketCategory = CategoryData(
            name = "BitBucket",
            thumbnailUrl = "BITBUCKET",
            ownerId = "George",
            defaultCategory = true
        )
        val cloudCategory = CategoryData(
            name = "Cloud",
            thumbnailUrl = "CLOUD",
            ownerId = "George",
            defaultCategory = true
        )
        val mailCategory = CategoryData(
            name = "Mail",
            thumbnailUrl = "MAILBOX",
            ownerId = "George",
            defaultCategory = true
        )
        val desktopCategory = CategoryData(
            name = "Desktop Mac",
            thumbnailUrl = "DESKTOP_MAC",
            ownerId = "George",
            defaultCategory = true
        )
        val laptopCategory = CategoryData(
            name = "Laptop Windows",
            thumbnailUrl = "LAPTOP_WINDOWS",
            ownerId = "George",
            defaultCategory = true
        )
        val towerCategory = CategoryData(
            name = "Desktop Tower",
            thumbnailUrl = "DESKTOP_TOWER",
            ownerId = "George",
            defaultCategory = true
        )
        val remoteCategory = CategoryData(
            name = "Remote Desktop",
            thumbnailUrl = "REMOTE_DESKTOP",
            ownerId = "George",
            defaultCategory = true
        )

        val field1 = FieldContentModel(
            name = "Username",
            type = 1,
            hidden = false,
            content = "v.boudis1995@gmail.com"
        )
        val field2 = FieldContentModel(
            name = "Password",
            type = 1,
            hidden = true,
            content = "elamesa@123@"
        )
        val field3 = FieldContentModel(
            name = "Website",
            type = 1,
            hidden = false,
            content = "https://appleid.apple.com/"
        )
        fieldsContent = listOf(field1, field2, field3)

        categories = listOf(gamingCategory, subChannelCategory, bankCategory, networkCategory, alarmsCategory,
            homeCategory, wordPressCategory, accessPointsCategory, airportCategory, appleCategory,
            tabletCategory, phoneCategory, bitcoinCategory, bitBucketCategory, cloudCategory,
            mailCategory, desktopCategory, laptopCategory, towerCategory, remoteCategory)

        items = listOf(appleId, adobe, behance, dribble, facebook, instagram, youtube)

        thumbnailsUrls = listOf(appleThumb, adobeThumb, behanceThumb, dribbleThumb, facebookThumb, instagramThumb, youtubeThumb)

        thumbnails = thumbnailsUrls.mapIndexed { index, model ->
            val type = if (index % 2 == 0) 1 else 2
            ThumbnailModel(
                model,
                type
            )
        }
        val ath = thumbnails.toMutableList()
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