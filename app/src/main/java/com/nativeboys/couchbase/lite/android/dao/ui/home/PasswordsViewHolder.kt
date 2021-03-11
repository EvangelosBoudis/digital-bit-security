package com.nativeboys.couchbase.lite.android.dao.ui.home

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.PasswordModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerViewHolder

class PasswordsViewHolder(itemView: View) : RecyclerViewHolder<PasswordModel>(itemView) {

    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)
    private val websiteField = itemView.findViewById<TextView>(R.id.website_field)
    private val emailField = itemView.findViewById<TextView>(R.id.email_field)
    private val copyBtn = itemView.findViewById<ImageView>(R.id.copy_btn)

    init {
        copyBtn.setOnClickListener(this)
    }

    override fun bind(model: PasswordModel) {

        websiteField.text = model.webSite
        emailField.text = model.email

        Glide.with(itemView.context)
            .load(R.drawable.copy_icon)
            .into(copyBtn)

        Glide.with(itemView.context)
            .load(model.thumbnailUrl)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)
    }

}