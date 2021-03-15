package com.nativeboys.password.manager.ui.adapters.tags

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.AdapterTagModel
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class TagsViewHolder(itemView: View) : RecyclerViewHolder<AdapterTagModel>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: AdapterTagModel) {
        descriptionField.text = model.description
        val drawableResource = if (model.type == 2) R.drawable.selected_tag_shape else R.drawable.tag_shape
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