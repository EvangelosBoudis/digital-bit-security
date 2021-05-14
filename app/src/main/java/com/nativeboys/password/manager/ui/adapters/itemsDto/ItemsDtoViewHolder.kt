package com.nativeboys.password.manager.ui.adapters.itemsDto

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ItemDto
import com.nativeboys.password.manager.util.intoView
import com.nativeboys.uikit.rv.RecyclerViewHolder
import com.nativeboys.uikit.swipeRevealLayout.SwipeRevealLayout
import com.nativeboys.uikit.swipeRevealLayout.ViewBinderHelper

class ItemsDtoViewHolder(
    itemView: View,
    private val binder: ViewBinderHelper
) : RecyclerViewHolder<ItemDto>(itemView) {

    private val container: SwipeRevealLayout = itemView.findViewById(R.id.container)
    private val visibleView: ConstraintLayout = itemView.findViewById(R.id.visible_view)
    private val moreBtn: ImageView = itemView.findViewById(R.id.more_btn)
    private val editBtn: ImageView = itemView.findViewById(R.id.edit_btn)
    private val deleteBtn: ImageView = itemView.findViewById(R.id.delete_btn)

    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)
    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)

    init {
        moreBtn.setOnClickListener(this)
        visibleView.setOnClickListener(this)
        editBtn.setOnClickListener(this)
        deleteBtn.setOnClickListener(this)
    }

    override fun bind(model: ItemDto) {

        binder.bind(container, model.itemId)
        container.setLockDrag(true)

        Glide.with(itemView.context)
            .load(R.drawable.cell_shape)
            .intoView(visibleView)

        nameField.text = model.itemName
        descriptionField.text = model.itemDescription

        Glide.with(itemView.context)
            .load(model.thumbnailUrl)
            .transform(CenterCrop())
            .transition(DrawableTransitionOptions().crossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)

        Glide.with(itemView.context)
            .load(R.drawable.menu_icon)
            .into(moreBtn)

        Glide.with(itemView.context)
            .load(R.drawable.ic_baseline_edit_24)
            .into(editBtn)

        Glide.with(itemView.context)
            .load(R.drawable.ic_baseline_delete_24)
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