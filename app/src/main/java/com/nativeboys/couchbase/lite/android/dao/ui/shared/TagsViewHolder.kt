package com.nativeboys.couchbase.lite.android.dao.ui.shared

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.nativeboys.couchbase.lite.android.dao.R
import com.nativeboys.couchbase.lite.android.dao.data.TagModel
import com.nativeboys.couchbase.lite.android.dao.helper.rv.RecyclerViewHolder

class TagsViewHolder(itemView: View) : RecyclerViewHolder<TagModel>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: TagModel) {
        descriptionField.text = model.description
        val drawableResource = if (absoluteAdapterPosition == 1) R.drawable.selected_tag_shape else R.drawable.tag_shape
        Glide
            .with(itemView.context)
            .load(drawableResource)
            .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                    ) {
                        descriptionField.background = resource
                    }
                    override fun onLoadCleared(placeholder: Drawable?) { }
            })
        // descriptionField.setBackgroundResource(drawableResource)
    }

}