package com.nativeboys.password.manager.ui.adapters.thumbnails

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.ThumbnailDto
import com.nativeboys.uikit.rv.RecyclerViewHolder

class ThumbnailsViewHolder(itemView: View) : RecyclerViewHolder<ThumbnailDto>(itemView) {

    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)
    private val foregroundView = itemView.findViewById<ShapeableImageView>(R.id.foreground_view)

    override fun bind(model: ThumbnailDto) {

        Glide.with(itemView.context)
            .load(model.url)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)

        foregroundView.visibility = if (model.type == 2) View.VISIBLE else View.INVISIBLE

        Glide.with(itemView.context)
            .load(R.drawable.ic_baseline_check_24)
            .into(foregroundView)

    }

}