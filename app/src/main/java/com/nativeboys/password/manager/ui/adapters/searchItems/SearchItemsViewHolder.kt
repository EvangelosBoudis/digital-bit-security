package com.nativeboys.password.manager.ui.adapters.searchItems

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemEntity
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class SearchItemsViewHolder(itemView: View) : RecyclerViewHolder<ItemEntity>(itemView) {

    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)
    private val nameField = itemView.findViewById<TextView>(R.id.name_field)

    override fun bind(model: ItemEntity) {

        nameField.text = model.name

        Glide.with(itemView.context)
            .load(model.thumbnailId)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)

    }

}