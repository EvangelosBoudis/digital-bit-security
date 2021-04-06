package com.nativeboys.password.manager.ui.adapters.itemsDto

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.ui.adapters.SwipeRevealViewHolder
import com.zeustech.zeuskit.ui.swipeRevealLayout.ViewBinderHelper

class ItemsDtoViewHolder(
    itemView: View,
    private val binder: ViewBinderHelper
) : SwipeRevealViewHolder<ItemDto>(itemView) {

    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)
    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)

    override fun bind(model: ItemDto) {
        super.bind(model)
        binder.bind(container, model.itemId)
        container.setLockDrag(true)

        nameField.text = model.itemName
        descriptionField.text = model.itemDescription

        Glide.with(itemView.context)
            .load(model.thumbnailUrl)
            .transform(CenterCrop())
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)
    }

}