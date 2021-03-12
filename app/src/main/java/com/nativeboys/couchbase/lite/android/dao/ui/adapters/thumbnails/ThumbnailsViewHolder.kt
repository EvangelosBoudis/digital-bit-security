package com.nativeboys.couchbase.lite.android.dao.ui.adapters.thumbnails

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.imageview.ShapeableImageView
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.AdapterThumbnailModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerViewHolder

class ThumbnailsViewHolder(itemView: View) : RecyclerViewHolder<AdapterThumbnailModel>(itemView) {

    private val thumbnailHolder = itemView.findViewById<ShapeableImageView>(R.id.thumbnail_holder)
    private val foregroundView = itemView.findViewById<ShapeableImageView>(R.id.foreground_view)

    override fun bind(model: AdapterThumbnailModel) {

        Glide.with(itemView.context)
            .load(model.url)
            .transform(CenterCrop())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(thumbnailHolder)

        foregroundView.visibility = if (model.type == 2) View.VISIBLE else View.INVISIBLE

        Glide.with(itemView.context)
            .load(R.drawable.check_icon_2)
            .into(foregroundView)

    }

}