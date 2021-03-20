package com.nativeboys.password.manager.ui.adapters.tags

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.TagModel
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class TagsViewHolder(itemView: View) : RecyclerViewHolder<TagModel>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: TagModel) {
        descriptionField.text = model.name
        Glide
            .with(itemView.context)
            .load(R.drawable.tag_shape)
            .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        descriptionField.background = resource
                    }
                    override fun onLoadCleared(placeholder: Drawable?) { }
            })
    }

}