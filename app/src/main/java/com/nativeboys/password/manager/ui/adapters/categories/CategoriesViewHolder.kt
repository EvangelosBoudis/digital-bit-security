package com.nativeboys.password.manager.ui.adapters.categories

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryData
import com.nativeboys.password.manager.other.materialIconCodeToDrawable
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder
import net.steamcrafted.materialiconlib.MaterialIconView

class CategoriesViewHolder(itemView: View) : RecyclerViewHolder<CategoryData>(itemView) {

    private val thumbnailHolder = itemView.findViewById<MaterialIconView>(R.id.thumbnail_holder)
    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val nextBtn = itemView.findViewById<ImageView>(R.id.next_btn)

    override fun bind(model: CategoryData) {
        nameField.text = model.name
        Glide.with(itemView.context)
            .load(R.drawable.next_icon)
            .into(nextBtn)
        val drawable = materialIconCodeToDrawable(itemView.context, model.thumbnailCode)
        if (drawable != null) {
            Glide
                .with(itemView.context)
                .load(drawable)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        thumbnailHolder.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        } else {
            thumbnailHolder.setImageDrawable(null)
        }
    }

}