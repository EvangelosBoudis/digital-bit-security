package com.nativeboys.password.manager.ui.adapters.filters

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.FilterModel
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder

class FiltersViewHolder(itemView: View) : RecyclerViewHolder<FilterModel>(itemView) {

    private val descriptionField = itemView.findViewById<TextView>(R.id.description_field)

    override fun bind(model: FilterModel) {
        descriptionField.text = model.description
        val drawableResource = if (model.selected) R.drawable.selected_tag_shape else R.drawable.tag_shape
        Glide
            .with(itemView.context)
            .load(drawableResource)
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