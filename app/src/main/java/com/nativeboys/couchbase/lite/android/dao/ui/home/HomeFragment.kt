package com.nativeboys.couchbase.lite.android.dao.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.PasswordModel
import com.nativeboys.couchbase.lite.android.dao.data.TagModel
import com.nativeboys.couchbase.lite.android.dao.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val tagsAdapter = TagsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        binding.tagsRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        binding.tagsRecyclerView.adapter = tagsAdapter
        applyMockData()
    }

    private fun applyMockData() {
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
            thumbnailUrl = "https://e7.pngegg.com/pngimages/566/77/png-clipart-apple-logo-apple-watch-logo-apple-logo-heart-logo-thumbnail.png"
        )
        val adobe = PasswordModel(
            webSite = "Adobe",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = "https://2.img-dpreview.com/files/p/E~TS940x788~articles/2988339509/BlogHeader_150-1-1800x0-c-default_copy.jpeg"
        )
        val behance = PasswordModel(
            webSite = "Behance",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = "https://cdn3.iconfinder.com/data/icons/picons-social/57/77-behance-512.png"
        )
        val dribble = PasswordModel(
            webSite = "Dribbble",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = "https://www.logolynx.com/images/logolynx/17/17bd9deba688043e6b996413deff3a50.png"
        )
        val facebook = PasswordModel(
            webSite = "Facebook",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = "https://facebookbrand.com/wp-content/uploads/2019/04/f_logo_RGB-Hex-Blue_512.png?w=512&h=512"
        )
        val instagram = PasswordModel(
            webSite = "Instagram",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = "https://i.pinimg.com/originals/76/00/8b/76008bb9685d410d47fe1fa01dc54f15.jpg"
        )
        val youtube = PasswordModel(
            webSite = "Youtube",
            email = "yasirbugra@gmail.com",
            password = "Yasir123%'/PzhL921",
            notes = "this is a note bla bla...",
            tagIds = "${social.id}, ${personal.id}",
            thumbnailUrl = "https://logos-world.net/wp-content/uploads/2020/04/YouTube-Emblem.png"
        )
        tagsAdapter.dataSet = listOf(
            social, masterPass, bank, shopping, personal, bills
        )
        listOf(
            appleId, adobe, behance, dribble, facebook, instagram, youtube
        )
    }


}