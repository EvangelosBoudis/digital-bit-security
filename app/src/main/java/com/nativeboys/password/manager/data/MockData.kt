package com.nativeboys.password.manager.data

object MockData {

    val items: List<ItemModel>

    val tags: List<TagModel>
    val adapterTags: List<AdapterTagModel>
    val adapterTagsWithAdd: List<AdapterTagModel>

    val thumbnails: List<String>
    val adapterThumbnails: List<AdapterThumbnailModel>
    val adapterThumbnailsWithAdd: List<AdapterThumbnailModel>

    val categories: List<CategoryData>
    val contentFields: List<ContentFieldModel>

    init {

        val appleThumb = "https://imagens.canaltech.com.br/empresas/838.400.jpg"
        val adobeThumb = "https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg"
        val behanceThumb = "https://dist.neo4j.com/wp-content/uploads/20180720064210/belogo-social-posts-default.png"
        val dribbleThumb = "https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png"
        val facebookThumb = "https://seeklogo.com/images/F/facebook-icon-logo-C61047A9E7-seeklogo.com.png"
        val instagramThumb = "https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg"
        val youtubeThumb = "https://i.pinimg.com/originals/83/0e/e1/830ee1e682300a661955fce4011bf9b3.jpg"

        val social =
            TagModel(description = "Social")
        val masterPass =
            TagModel(description = "Master Passwords")
        val bank =
            TagModel(description = "Bank")
        val shopping =
            TagModel(description = "Shopping")
        val personal =
            TagModel(description = "Personal")
        val bills =
            TagModel(description = "Biils")

        val appleId = ItemModel(
            webSite = "Apple ID",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = appleThumb
        )
        val adobe = ItemModel(
            webSite = "Adobe",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = adobeThumb
        )
        val behance = ItemModel(
            webSite = "Behance",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = behanceThumb
        )
        val dribble = ItemModel(
            webSite = "Dribbble",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = dribbleThumb
        )
        val facebook = ItemModel(
            webSite = "Facebook",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = facebookThumb
        )
        val instagram = ItemModel(
            webSite = "Instagram",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = instagramThumb
        )
        val youtube = ItemModel(
            webSite = "Youtube",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
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

        val field1 = ContentFieldModel(
            name = "Username",
            type = 1,
            hidden = false,
            content = "v.boudis1995@gmail.com"
        )
        val field2 = ContentFieldModel(
            name = "Password",
            type = 1,
            hidden = true,
            content = "elamesa@123@"
        )
        val field3 = ContentFieldModel(
            name = "Website",
            type = 1,
            hidden = false,
            content = "https://appleid.apple.com/"
        )
        contentFields = listOf(field1, field2, field3)

        categories = listOf(gamingCategory, subChannelCategory, bankCategory, networkCategory, alarmsCategory,
            homeCategory, wordPressCategory, accessPointsCategory, airportCategory, appleCategory,
            tabletCategory, phoneCategory, bitcoinCategory, bitBucketCategory, cloudCategory,
            mailCategory, desktopCategory, laptopCategory, towerCategory, remoteCategory)

        items = listOf(appleId, adobe, behance, dribble, facebook, instagram, youtube)

        tags = listOf(social, masterPass, bank, shopping, personal, bills)
        adapterTags = tags.mapIndexed { index, model ->
            val type = if (index % 2 == 0) 1 else 2
            AdapterTagModel(model, type)
        }
        val at = adapterTags.toMutableList()
        at.add(AdapterTagModel(type = 3))
        adapterTagsWithAdd = at

        thumbnails = listOf(appleThumb, adobeThumb, behanceThumb, dribbleThumb, facebookThumb, instagramThumb, youtubeThumb)
        adapterThumbnails = thumbnails.mapIndexed { index, model ->
            val type = if (index % 2 == 0) 1 else 2
            AdapterThumbnailModel(
                model,
                type
            )
        }
        val ath = adapterThumbnails.toMutableList()
        ath.add(AdapterThumbnailModel(type = 3))
        adapterThumbnailsWithAdd = ath

    }

}