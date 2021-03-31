package com.nativeboys.password.manager.ui.adapters.items

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemData
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder
import com.zeustech.zeuskit.ui.swipeRevealLayout.SwipeRevealLayout
import com.zeustech.zeuskit.ui.swipeRevealLayout.ViewBinderHelper

class ItemsViewHolder(
    itemView: View,
    private val binder: ViewBinderHelper
) : RecyclerViewHolder<ItemData>(itemView) {

    private val container = itemView.findViewById<SwipeRevealLayout>(R.id.container)

    private val visibleView = itemView.findViewById<ConstraintLayout>(R.id.visible_view)
    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)
    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)
    private val moreBtn = itemView.findViewById<ImageView>(R.id.more_btn)

    private val editBtn = itemView.findViewById<ImageView>(R.id.edit_btn)
    private val deleteBtn = itemView.findViewById<ImageView>(R.id.delete_btn)

    init {
        moreBtn.setOnClickListener(this)
        visibleView.setOnClickListener(this)
        editBtn.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)
    }

    override fun bind(model: ItemData) {
        binder.bind(container, model.id)
        container.setLockDrag(true)

        nameField.text = model.name
        descriptionField.text = model.description

        Glide.with(itemView.context)
            .load(model.thumbnailId)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)

        Glide.with(itemView.context)
            .load(R.drawable.menu_icon)
            .into(moreBtn)

        Glide.with(itemView.context)
            .load(R.drawable.edit_icon)
            .into(editBtn)

        Glide.with(itemView.context)
            .load(R.drawable.trash_can_icon)
            .into(deleteBtn)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.more_btn -> {
                if (container.isOpened) container.close(true)
                else container.open(true)
            }
            R.id.visible_view -> {
                if (container.isOpened) container.close(true)
                else super.onClick(v)
            }
            else -> {
                super.onClick(v)
            }
        }
    }

}