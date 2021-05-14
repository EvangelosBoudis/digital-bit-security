package com.nativeboys.password.manager.ui.adapters.categoriesIconSearch

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nativeboys.password.manager.R
import com.nativeboys.password.manager.data.UICategoryIcon
import com.nativeboys.password.manager.util.intoMaterialIcon
import com.nativeboys.password.manager.util.intoView
import com.nativeboys.password.manager.util.materialIconCodeToDrawable
import com.nativeboys.uikit.rv.RecyclerViewHolder
import net.steamcrafted.materialiconlib.MaterialIconView

class CategoriesIconSearchViewHolder(
    itemView: View
) : RecyclerViewHolder<UICategoryIcon>(itemView) {

    private val thumbnailBackgroundHolder = itemView.findViewById<View>(R.id.thumbnail_background_holder)
    private val thumbnailHolder = itemView.findViewById<MaterialIconView>(R.id.thumbnail_holder)

    override fun bind(model: UICategoryIcon) {
        val iconColor =
            when {
                model.selected -> ContextCompat.getColor(itemView.context, R.color.colorPrimary)
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES -> Color.WHITE
                else -> Color.BLACK
            }
        val draw = materialIconCodeToDrawable(
            itemView.context,
            model.thumbnailCode,
            iconColor,
            30
        )
        if (draw != null) {
            Glide.with(itemView.context)
                .load(draw)
                .transition(DrawableTransitionOptions().crossFade())
                .intoMaterialIcon(thumbnailHolder)
        } else {
            thumbnailHolder.setImageDrawable(null)
        }
        Glide
            .with(itemView.context)
            .load(ContextCompat.getDrawable(itemView.context, R.drawable.stroke_shape))
            .intoView(thumbnailBackgroundHolder)
    }

}