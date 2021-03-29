package com.nativeboys.password.manager.ui.adapters.categories

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.CategoryEntity
import com.zeustech.zeuskit.ui.rv.RecyclerViewHolder
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder
import net.steamcrafted.materialiconlib.MaterialIconView

class CategoriesViewHolder(itemView: View) : RecyclerViewHolder<CategoryEntity>(itemView) {

    private val thumbnailHolder = itemView.findViewById<MaterialIconView>(R.id.thumbnail_holder)
    private val nameField = itemView.findViewById<TextView>(R.id.name_field)
    private val nextBtn = itemView.findViewById<ImageView>(R.id.next_btn)

    override fun bind(model: CategoryEntity) {
        nameField.text = model.name
        val icon = try {
            MaterialDrawableBuilder.IconValue.valueOf(model.thumbnailCode)
        } catch (e: IllegalArgumentException) {
            null
        }
        Glide.with(itemView.context)
            .load(R.drawable.next_icon)
            .into(nextBtn)
        if (icon != null) {
            val drawable = MaterialDrawableBuilder.with(itemView.context) // provide a context
                .setIcon(icon) // provide an icon
                .setColor(Color.WHITE) // set the icon color
                .setToActionbarSize() // set the icon size
                .build() // Finally call build
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