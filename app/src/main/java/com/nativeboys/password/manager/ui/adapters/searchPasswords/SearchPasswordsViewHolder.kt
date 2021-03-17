package com.nativeboys.password.manager.ui.adapters.searchPasswords

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.PasswordModel
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class SearchPasswordsViewHolder(itemView: View) : RecyclerViewHolder<PasswordModel>(itemView) {

    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)
    private val websiteField = itemView.findViewById<TextView>(R.id.website_field)

    override fun bind(model: PasswordModel) {

        websiteField.text = model.webSite

        Glide.with(itemView.context)
            .load(model.thumbnailUrl)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)

    }

}