package com.nativeboys.couchbase.lite.android.dao.data

object MockData {

    val passwords: List<PasswordModel>

    val tags: List<TagModel>
    val adapterTags: List<AdapterTagModel>
    val adapterTagsWithAdd: List<AdapterTagModel>

    val thumbnails: List<String>
    val adapterThumbnails: List<AdapterThumbnailModel>
    val adapterThumbnailsWithAdd: List<AdapterThumbnailModel>

    init {

        val appleThumb = "https://lh3.googleusercontent.com/proxy/YXD9HvWRYddjuVxbFdUa8qfftI5I3IlPvovU2ZH9ObPHHSzGilUM8hcLOvNbhjjBuik5SfShz9fW2TTIbzz3WBU1YqGyc4c"
        val adobeThumb = "https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg"
        val behanceThumb = "https://dist.neo4j.com/wp-content/uploads/20180720064210/belogo-social-posts-default.png"
        val dribbleThumb = "https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png"
        val facebookThumb = "https://seeklogo.com/images/F/facebook-icon-logo-C61047A9E7-seeklogo.com.png"
        val instagramThumb = "https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg"
        val youtubeThumb = "https://i.pinimg.com/originals/83/0e/e1/830ee1e682300a661955fce4011bf9b3.jpg"

        val social = TagModel(description = "Social")
        val masterPass = TagModel(description = "Master Passwords")
        val bank = TagModel(description = "Bank")
        val shopping = TagModel(description = "Shopping")
        val personal = TagModel(description = "Personal")
        val bills = TagModel(description = "Biils")

        val appleId = PasswordModel(
            webSite = "Apple ID",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = appleThumb
        )
        val adobe = PasswordModel(
            webSite = "Adobe",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = adobeThumb
        )
        val behance = PasswordModel(
            webSite = "Behance",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = behanceThumb
        )
        val dribble = PasswordModel(
            webSite = "Dribbble",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = dribbleThumb
        )
        val facebook = PasswordModel(
            webSite = "Facebook",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = facebookThumb
        )
        val instagram = PasswordModel(
            webSite = "Instagram",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = instagramThumb
        )
        val youtube = PasswordModel(
            webSite = "Youtube",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = youtubeThumb
        )

        passwords = listOf(appleId, adobe, behance, dribble, facebook, instagram, youtube)

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
            AdapterThumbnailModel(model, type)
        }
        val ath = adapterThumbnails.toMutableList()
        ath.add(AdapterThumbnailModel(type = 3))
        adapterThumbnailsWithAdd = ath

    }

}